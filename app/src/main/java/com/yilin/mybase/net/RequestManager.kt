package com.yilin.mybase.net

import android.util.Log
import com.yilin.mybase.net.request.BaseRequest
import com.yilin.mybase.net.response.BaseResponse
import com.yilin.mybase.net.service.RetrofitConverter
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class RequestManager private constructor() {

    companion object {
        private const val TAG = "RequestManager"

        val instance: RequestManager by lazy { RequestManager() }
    }

    /***
     *  請求佇列，避免重複發送請求
     */
    private val requestQueue = hashMapOf<String, BaseRequest<*>>()

    /***
     *  apiService，把不同的service存起來
     */
    private val apiService = hashMapOf<String, Any>()

    private val mOkHttpClient =
        OkHttpClient().newBuilder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()

    private var mRetrofit: Retrofit? = null
    private lateinit var baseUrl: String

    /***
     *  對外唯一接口，須實作BaseRequest
     */
    suspend fun sendRequest(request: BaseRequest<*>): BaseResponse {
        val tag = request.tag
        if (requestQueue.containsKey(tag)) {
            return failedCallBack("重複發送請求")
        }
        requestQueue[tag] = request
        val call = request.getCall(getService(request))
        Log.i(TAG, " body= ${request.requestBody}")
        Log.i(TAG, " method= ${call.request().method}")
        Log.i(TAG, " url= ${call.request().url.toUrl()}")
        return suspendCancellableCoroutine {
            call.enqueue(object : Callback<String> {
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    requestQueue.remove(tag)
                    if (response.isSuccessful) {
                        Log.i(TAG, "response successCallBack----->")
                        val string = response.body().toString()
                        Log.i(TAG, "response ----->$string")
                        it.resumeWith(Result.success(successCallBack(string)))
                    } else {
                        Log.i(TAG, "response Exception----->" + response.code())
                        it.resumeWith(Result.success(failedCallBack("服務器錯誤")))
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.i(TAG, t.toString())
                    requestQueue.remove(tag)
                    it.resumeWith(Result.success(failedCallBack("訪問失敗")))
                }

            })
            it.invokeOnCancellation {
                requestQueue.remove(tag)
                call.cancel()
            }
        }
    }

    private fun checkBaseUrl(newBaseUrl: String) {
        this.baseUrl = newBaseUrl
        if (mRetrofit == null) {
            mRetrofit = Retrofit.Builder()
                .baseUrl(newBaseUrl)
                .client(mOkHttpClient)
                .callFactory { request ->
                    //替换不同的baseURL，第一个打的api为BASE URL
                    val nowUrl = request.url.toUrl().toString()
                    if (!nowUrl.contains(baseUrl) && mRetrofit != null) {
                        val oldBaseUrl = mRetrofit!!.baseUrl().toUrl().toString()
                        val newUrl = nowUrl.replace(oldBaseUrl, baseUrl)
                        val newHttpUrl = newUrl.toHttpUrl()
                        val newRequest = request.newBuilder().url(newHttpUrl).build()
                        mOkHttpClient.newCall(newRequest)
                    } else {
                        mOkHttpClient.newCall(request)
                    }
                }
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(RetrofitConverter())
                .build()
        }
    }

    private fun getService(request: BaseRequest<*>): Any {
        checkBaseUrl(request.baseUrl)
        val service = request.getServiceClass()
        val apiKey = service.javaClass.simpleName
        if (!apiService.containsKey(apiKey)) {
            apiService[apiKey] = mRetrofit!!.create(service) as Any
        }
        return apiService[apiKey]!!
    }

    private fun failedCallBack(msg: String) = BaseResponse(ResultCode.RESULT_CODE_FAIL, msg)

    private fun successCallBack(msg: String) = BaseResponse(msg, ResultCode.RESULT_CODE_SUCCESS)

}