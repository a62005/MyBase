package com.yilin.mybase.viewmodel.respository

import com.yilin.common.net.RequestManager
import com.yilin.mybase.bean.PokemonBean
import com.yilin.mybase.net.request.PokemonRequest
import javax.inject.Singleton

@Singleton
class MainRemoteSource {
    private val requestManager: RequestManager by lazy { RequestManager.instance }

    suspend fun getPokemonList(): List<PokemonBean> {
        val request = PokemonRequest()
        val response = requestManager.sendRequest(request)
        return response.data ?: emptyList()
    }
}