package com.yilin.mybase.viewmodel.respository

import com.yilin.mybase.net.RequestManager
import javax.inject.Singleton

@Singleton
class MainRemoteSource {
    private val requestManager: RequestManager by lazy { RequestManager.instance }
}