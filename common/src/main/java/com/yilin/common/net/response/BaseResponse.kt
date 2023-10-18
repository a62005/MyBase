package com.yilin.common.net.response

import com.yilin.common.net.ResultCode
import com.yilin.common.utils.JsonParseUtil
import org.json.JSONObject


/***
 * @param data It's callback data from net
 * @param resultCode 0 = fail, 1 = success
 * @param errorMsg Error message
 */
open class BaseResponse<T>(
    val data: T? = null,
    val message: String? = null,
    val resultCode: Int,
    val errorMsg: String? = null
) {

    constructor(resultCode: Int, errorMsg: String?) : this(null, null, resultCode, errorMsg ?: "網路不穩，請檢查網路")
    constructor(response: BaseResponse<T>) : this(
        response.data,
        response.message,
        response.resultCode,
        response.errorMsg
    )

    val isSuccess: Boolean
        get() = resultCode == ResultCode.RESULT_CODE_SUCCESS

    val jsonObject: JSONObject
        get() {
            return if (data is String && JsonParseUtil.isJsonData(data)) {
                JSONObject(data)
            } else {
                JSONObject()
            }
        }
}