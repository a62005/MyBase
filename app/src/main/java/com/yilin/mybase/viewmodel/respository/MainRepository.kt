package com.yilin.mybase.viewmodel.respository

import com.yilin.mybase.bean.message.MessageBean
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(
    private val ioScope: CoroutineScope,
    private val mainLocalSource: MainLocalSource,
    private val mainRemoteSource: MainRemoteSource
) {

    fun addMessage(messageBean: MessageBean) {
        mainLocalSource.insert(messageBean)
    }

    suspend fun getPokemonList() = withContext(ioScope.coroutineContext) {
        val data = mainLocalSource.getPokemonList()
        data.ifEmpty {
            val resp = mainRemoteSource.getPokemonList()
            resp.pokemonList.apply {
                mainLocalSource.insert(this)
            }
        }
    }

    suspend fun getMessageList() = withContext(ioScope.coroutineContext) {
        mainLocalSource.getMessageList()
    }

    fun loadMessageUnreadCount() = mainLocalSource.loadMessageUnreadCount()
}

