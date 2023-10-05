package com.yilin.mybase.utils

import java.util.Calendar

object DateUtil {

    fun getCurrentTime(): String {
        val c = Calendar.getInstance()
        c.time.time = System.currentTimeMillis()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val min = c.get(Calendar.MINUTE)
        return "yyyy-MM-dd hh:mm".format(year, month, day, hour, min)
    }
}