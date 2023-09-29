package com.yilin.mybase.utils

import java.util.regex.Pattern

object JsonParseUtil {
    /**
     * 判断返回的数据是不是json格式数据
     */
    fun isJsonData(data: String): Boolean {
        if (data.isEmpty()) {
            return false
        }
        return if (data.startsWith("{") && data.endsWith("}")) {
            true
        } else {
            data.startsWith("[") && data.endsWith("]")
        }
    }

    fun isNumeric(str: String): Boolean {
        val pattern = Pattern.compile("[0-9]*")
        val isNum = pattern.matcher(str)
        return isNum.matches()
    }
}