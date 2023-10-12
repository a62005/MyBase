package com.yilin.mybase.viewmodel

import androidx.lifecycle.LiveData
import com.yilin.common.viewmodel.BaseViewModel
import com.yilin.common.enevt.MyEvent
import com.yilin.mybase.viewmodel.respository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainRepository) : BaseViewModel() {

    val onMessageListener: LiveData<Int> get() = repository.loadMessageUnreadCount()

    override fun onEvent(event: MyEvent) {

    }
}