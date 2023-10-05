package com.yilin.mybase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.yilin.mybase.bean.CalendarNoteBean
import com.yilin.mybase.viewmodel.respository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(private val repository: MainRepository): ViewModel() {

    private val _onNoteDateListener = MutableLiveData<Long>()
    val onNoteListListener: LiveData<List<CalendarNoteBean>>
        get() = _onNoteDateListener.switchMap {
        repository.loadCalendarNoteList(it)
    }

    val onNoteDateListener: LiveData<List<Long>> get() = repository.loadCalendarNoteDateList()


    fun setDateTime(dateTime: Long) {
        _onNoteDateListener.value = dateTime
    }

    fun deleteNote(noteId: Int) {
        repository.deleteCalendarNote(noteId)
    }
}