package com.yilin.mybase.viewmodel.respository

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(
    private val mainLocalSource: MainLocalSource,
    private val mainRemoteSource: MainRemoteSource
)

