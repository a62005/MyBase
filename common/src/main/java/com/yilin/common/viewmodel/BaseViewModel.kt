package com.yilin.common.viewmodel

import android.util.Log
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

    companion object {
        private const val TAG = "BaseViewModel"
    }

    protected fun sendApi(api: suspend () -> BaseResponse<*>, handle: (BaseResponse<*>) -> Unit) {
        val response = viewModelScope.async { api() }
        viewModelScope.launch {
            handle(response.await())
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onEvent(event: MyEvent) {
        Log.i(TAG, "Receive Event : $event")
        onEventListener(event)
    }

    protected open fun onEventListener(event: MyEvent) {

    }

    protected fun registerEvent() {
        EventBus.getDefault().let { bus ->
            if (!bus.isRegistered(this)) {
                bus.register(this)
                addCloseable {
                    bus.unregister(this)
                }
            }
        }
    }
}

