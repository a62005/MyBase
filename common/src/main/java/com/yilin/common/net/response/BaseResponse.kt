package com.yilin.common.net.response

import com.yilin.common.net.ResultCode
import org.json.JSONObject


/***
 * @param data It's callback data from net
 * @param resultCode 0 = fail, 1 = success
 * @param errorMsg Error message
 */
open class BaseResponse(
    val data: String,
    val resultCode: Int,
    val errorMsg: String? = null
) {

    constructor(resultCode: Int, errorMsg: String?) : this("", resultCode, errorMsg ?: "網路不穩，請檢查網路")
    constructor(response: BaseResponse) : this(
        response.data,
        response.resultCode,
        response.errorMsg
    )

    val isSuccess: Boolean
        get() = resultCode == ResultCode.RESULT_CODE_SUCCESS

    val jsonObject: JSONObject
        get() = JSONObject(data)
}