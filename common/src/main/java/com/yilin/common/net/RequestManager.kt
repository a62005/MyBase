package com.yilin.common.net

import android.util.Log
import com.yilin.common.net.request.BaseRequest
import com.yilin.common.net.response.BaseResponse
import com.yilin.common.net.service.BaseService
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class RequestManager private constructor() {

    companion object {
        private const val TAG = "RequestManager"

        val instance: RequestManager by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { RequestManager() }
    }

    /***
     *  請求佇列，避免重複發送請求
     */
    private val requestQueue = hashMapOf<String, BaseRequest<*, *>>()

    /***
     *  apiService，把不同的service存起來
     */
    private val apiService = hashMapOf<String, BaseService>()

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
    suspend fun <T> sendRequest(request: BaseRequest<*, T>): BaseResponse<T> {
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
            call.enqueue(object : Callback<T> {
                override fun onResponse(
                    call: Call<T>,
                    response: Response<T>
                ) {
                    requestQueue.remove(tag)
                    if (response.isSuccessful && response.body() != null) {
                        val body = response.body()!!
                        Log.i(TAG, "response successCallBack----->")
                        val string = body.toString()
                        Log.i(TAG, "response ----->$string")
                        it.resumeWith(Result.success(successCallBack(body, "請求成功")))
                    } else {
                        Log.i(TAG, "response Exception----->" + response.code())
                        it.resumeWith(Result.success(failedCallBack("服務器錯誤")))
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
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
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(RetrofitConverter())
                .build()
        }
    }

    private fun getService(request: BaseRequest<*, *>): BaseService {
        checkBaseUrl(request.baseUrl)
        val service = request.getServiceClass()
        val apiKey = service.javaClass.simpleName
        if (!apiService.containsKey(apiKey)) {
            apiService[apiKey] = mRetrofit!!.create(service)
        }
        return apiService[apiKey]!!
    }

    private fun <T> failedCallBack(msg: String) = BaseResponse<T>(ResultCode.RESULT_CODE_FAIL, msg)

    private fun <T> successCallBack(data: T, msg: String) = BaseResponse(data, message = msg, ResultCode.RESULT_CODE_SUCCESS)

}