package com.yilin.common.net.request

import com.yilin.common.net.service.BaseService
import org.json.JSONObject
import retrofit2.Call

/***
 * @param K which one service
 * @param T what kind of type from response
 */
abstract class BaseRequest<K : BaseService, T> {

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
    suspend fun getCall(clazz: BaseService): Call<T> {
        return getServiceMethod(clazz as K)
    }

    /***
     * 標示請求依賴於哪個SERVICE
     */
    abstract fun getServiceClass(): Class<K>

    /***
     * 實際提供SERVICE裡的方法给getCall
     */
    protected abstract suspend fun getServiceMethod(clazz: K): Call<T>
}