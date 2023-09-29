package com.yilin.mybase.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yilin.mybase.net.response.BaseResponse
import com.yilin.mybase.viewmodel.respository.MainRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

abstract class BaseViewModel(repository: MainRepository) : ViewModel() {

    protected fun sendApi(api: suspend () -> BaseResponse, handle: (BaseResponse) -> Unit) {
        val response = viewModelScope.async { api() }
        viewModelScope.launch {
            handle(response.await())
        }
    }

}

