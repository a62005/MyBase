package com.yilin.common.net.request

import org.json.JSONObject
import retrofit2.Call

abstract class BaseRequest<T> {

    /***
     * 用於標示請求
     */
    val tag: String
        get() = this::class.java.simpleName

    abstract val baseUrl: String
    abstract val requestBody: JSONObject

    /***
     * 外部取得API CALL的方法
     */
    suspend fun getCall(clazz: Any): Call<String> {
        return getServiceMethod(clazz as T)
    }

    /***
     * 標示請求依賴於哪個SERVICE
     */
    abstract fun getServiceClass(): Class<T>

    /***
     * 實際提供SERVICE裡的方法给getCall
     */
    protected abstract suspend fun getServiceMethod(clazz: T): Call<String>
}