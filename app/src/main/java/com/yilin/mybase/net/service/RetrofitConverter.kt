package com.yilin.mybase.net.service

import com.yilin.mybase.utils.JsonParseUtil
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class RetrofitConverter : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        return ResponseBodyConverter(type)
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody> {
        return RequestBodyConverter()
    }

    class RequestBodyConverter : Converter<Any, RequestBody> {

        companion object {
            private val MEDIA_TYPE_JSON_MOBI =
                "application/json; charset=utf-8".toMediaTypeOrNull() //media type 这个需要和服务端保持一致
        }

        override fun convert(value: Any): RequestBody {
            val data = value.toString()
            val requestBody = if (JsonParseUtil.isJsonData(data)) {
                data
            } else {
                ""
            }
            return requestBody.toRequestBody(MEDIA_TYPE_JSON_MOBI)
        }
    }

    class ResponseBodyConverter(private val type: Type) : Converter<ResponseBody, String> {
        override fun convert(value: ResponseBody): String {
            val responseBody = if (String::class.java == type) {
                value.string()
            } else {
                ""
            }
            return responseBody
        }
    }
}