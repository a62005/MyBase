package com.yilin.mybase.viewmodel

import androidx.lifecycle.LiveData
import com.yilin.mybase.viewmodel.enevt.MyEvent
import com.yilin.mybase.viewmodel.respository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainRepository) : BaseViewModel() {

    val onMessageListener: LiveData<Int> get() = repository.loadMessageUnreadCount()

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    override fun onEvent(event: MyEvent) {

    }
}