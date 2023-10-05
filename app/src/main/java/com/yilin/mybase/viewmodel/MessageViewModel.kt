package com.yilin.mybase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yilin.mybase.bean.message.MessageBean
import com.yilin.mybase.viewmodel.respository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(private val repository: MainRepository): ViewModel() {

    private val _onMessageListListener = MutableLiveData<List<MessageBean>>()
    val onMessageListListener: LiveData<List<MessageBean>> get() = _onMessageListListener

    init {
        viewModelScope.launch {
            _onMessageListListener.value = repository.getMessageList()
        }
    }
}