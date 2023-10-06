package com.yilin.mybase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.yilin.mybase.bean.message.MessageBean
import com.yilin.mybase.viewmodel.respository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    val onMessageListListener: LiveData<List<MessageBean>> get() = repository.loadMessageList()

    fun readAll() {
        onMessageListListener.value?.forEach {
            it.isRead = true
            repository.setMessageRead(it.id)
        }
    }

    fun deleteAll() {
        repository.deleteMessage()
    }

    fun read(id: Int) {
        repository.setMessageRead(id)
    }

    fun remove(id: Int) {
        repository.deleteMessage(id)
    }
}