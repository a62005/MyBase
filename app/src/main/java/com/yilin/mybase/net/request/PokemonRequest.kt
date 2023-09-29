package com.yilin.mybase.net.request

import com.yilin.mybase.net.service.PokemonService
import org.json.JSONObject
import retrofit2.Call

class PokemonRequest : BaseRequest<PokemonService>() {

    override val baseUrl: String
        get() = "https://gist.githubusercontent.com/mrcsxsiq/b94dbe9ab67147b642baa9109ce16e44/raw/97811a5df2df7304b5bc4fbb9ee018a0339b8a38"

    override val requestBody: JSONObject
        get() = JSONObject()

    override fun getServiceClass(): Class<PokemonService> {
        return PokemonService::class.java
    }

    override suspend fun getServiceMethod(clazz: PokemonService): Call<String> {
        return clazz.getPokemonList()
    }
}