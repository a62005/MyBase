package com.yilin.mybase.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yilin.mybase.R
import com.yilin.mybase.bean.CalendarNoteBean
import com.yilin.mybase.utils.DateUtil
import com.yilin.mybase.viewmodel.respository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CalendarNoteAddViewModel @Inject constructor(
    private val repository: MainRepository,
    application: Application
) : AndroidViewModel(application) {

    private val _onNoteListener = MutableLiveData<CalendarNoteBean?>(null)
    val onNoteListener: LiveData<CalendarNoteBean?> get() = _onNoteListener

    private val _onDateListener = MutableLiveData<String>()
    val onDateListener: LiveData<String> get() = _onDateListener

    private var dateTime: Long = 0L
    private val weekDay: Array<String> by lazy {
        getApplication<Application>().resources.getStringArray(
            R.array.yqb_calendar_weekdays
        )
    }


    fun setNote(id: Int) {
        viewModelScope.launch {
            _onNoteListener.value = repository.getCalendarNote(id)
        }
    }

    fun saveNote(title: String, content: String) {
        _onNoteListener.value?.let { note ->
            note.title = title
            note.content = content
            note.dateTime = dateTime
            repository.update(note)
        } ?: run {
            val note = CalendarNoteBean(title = title, content = content, dateTime = dateTime)
            repository.addNote(note)
        }
    }

    fun setDate(dateTime: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            this@CalendarNoteAddViewModel.dateTime = dateTime
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = dateTime
            _onDateListener.postValue(
                "${calendar.get(Calendar.YEAR)}/${calendar.get(Calendar.MONTH) + 1}/${
                    calendar.get(
                        Calendar.DAY_OF_MONTH
                    )
                } (${
                    getWeekDay(calendar.get(Calendar.DAY_OF_WEEK) - 1)
                })"
            )
        }

    }

    private fun getWeekDay(day: Int): String {
        return if (day >= weekDay.size) {
            weekDay.first()
        } else {
            weekDay[day]
        }
    }

    fun setNextDay() {
        setDate(dateTime + DateUtil.ONE_DAY_MILLISECOND)
    }

    fun setPreviousDay() {
        setDate(dateTime - DateUtil.ONE_DAY_MILLISECOND)
    }
}