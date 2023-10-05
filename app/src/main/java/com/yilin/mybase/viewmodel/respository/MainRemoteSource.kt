package com.yilin.mybase.viewmodel.respository

import com.yilin.mybase.net.RequestManager
import com.yilin.mybase.net.request.PokemonRequest
import com.yilin.mybase.net.response.PokemonResponse
import javax.inject.Singleton

@Singleton
class MainRemoteSource {
    private val requestManager: RequestManager by lazy { RequestManager.instance }

    suspend fun getPokemonList(): PokemonResponse {
        val request = PokemonRequest()
        val response = requestManager.sendRequest(request)
        return PokemonResponse(response)
    }
}