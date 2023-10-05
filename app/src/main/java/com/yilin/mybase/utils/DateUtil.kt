package com.yilin.mybase.utils

import java.util.*

object DateUtil {

    const val ONE_DAY_MILLISECOND: Long = 86400000

    fun getCurrentTime(): String {
        val c = Calendar.getInstance()
        c.timeInMillis = System.currentTimeMillis()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH) + 1
        val day = c.get(Calendar.DAY_OF_MONTH)
        val hour = formatZero(c.get(Calendar.HOUR_OF_DAY))
        val min = formatZero(c.get(Calendar.MINUTE))
        return "$year-$month-$day $hour:$min"
    }

    private fun formatZero(num: Int): String {
        return if (num < 10) {
            "0$num"
        } else {
            num.toString()
        }
    }

    fun getTodayStartMillisecond(): Long {
        val c = Calendar.getInstance()
        c.timeInMillis = System.currentTimeMillis()
        c.set(Calendar.HOUR_OF_DAY, 0)
        c.set(Calendar.MINUTE, 0)
        c.set(Calendar.SECOND, 0)
        c.set(Calendar.MILLISECOND, 0)
        return c.timeInMillis
    }

    fun parseLongToDate(time: Long): String {
        val c = Calendar.getInstance()
        c.timeInMillis = time
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH) + 1
        val day = c.get(Calendar.DAY_OF_MONTH)
        return "$year/$month/$day"
    }
}