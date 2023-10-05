package com.yilin.mybase.view.decoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.style.LineBackgroundSpan
import androidx.core.content.ContextCompat
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.yilin.mybase.R

class MyCalendarSelectedDecorator(
    context: Context,
    currentTime: Long,
    private var dateList: List<Long>
) : MyCalendarDecorator(context, currentTime) {

    override fun getBackground(): Int {
        return R.drawable.inset_calendar_seleted
    }

    override fun shouldDecorate(day: CalendarDay): Boolean {
        val ms = day.calendar.timeInMillis
        return dateList.contains(ms) && ms != currentTime
    }

    fun updateData(data: List<Long>) {
        dateList = data
    }
}

class MyCalendarSelectedNowDecorator(context: Context, currentTime: Long) :
    MyCalendarDecorator(context, currentTime) {

    override fun getBackground(): Int {
        return R.drawable.inset_calendar_current_seleted
    }

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return day.calendar.timeInMillis == currentTime
    }
}

abstract class MyCalendarDecorator(private val context: Context, protected var currentTime: Long) :
    DayViewDecorator {

    abstract fun getBackground(): Int

    override fun decorate(view: DayViewFacade) {
        ContextCompat.getDrawable(context, getBackground())?.let {
            view.addSpan(MyDateSpan())
            view.setBackgroundDrawable(it)
        }
    }

    private class MyDateSpan : LineBackgroundSpan {
        override fun drawBackground(
            canvas: Canvas,
            paint: Paint,
            left: Int,
            right: Int,
            top: Int,
            baseline: Int,
            bottom: Int,
            text: CharSequence,
            start: Int,
            end: Int,
            lineNumber: Int
        ) {
            paint.color = Color.WHITE
        }
    }

    fun setCurrentDate(time: Long) {
        currentTime = time
    }
}