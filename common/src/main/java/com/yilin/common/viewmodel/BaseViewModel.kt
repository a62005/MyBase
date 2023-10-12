package com.yilin.common.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yilin.common.enevt.MyEvent
import com.yilin.common.net.response.BaseResponse
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class BaseViewModel : ViewModel() {

    private var eventBus: EventBus? = null

    protected fun sendApi(api: suspend () -> BaseResponse, handle: (BaseResponse) -> Unit) {
        val response = viewModelScope.async { api() }
        viewModelScope.launch {
            handle(response.await())
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    protected open fun onEvent(event: MyEvent) {

    }

    protected fun registerEvent() {
        EventBus.getDefault().apply {
            eventBus = this
            register(this@BaseViewModel)
        }
    }

    override fun onCleared() {
        super.onCleared()
        eventBus?.unregister(this)
    }
}

