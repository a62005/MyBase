package com.yilin.mybase.viewmodel.respository

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

    suspend fun getPokemonList() = withContext(ioScope.coroutineContext) {
        val data = mainLocalSource.getPokemonList()
        data.ifEmpty {
            val resp = mainRemoteSource.getPokemonList()
            resp.pokemonList.apply {
                mainLocalSource.insert(this)
            }
        }
    }
}

