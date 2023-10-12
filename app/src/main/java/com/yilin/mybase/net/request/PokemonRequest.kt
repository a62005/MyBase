package com.yilin.mybase.net.request

import com.yilin.common.net.request.BaseRequest
import com.yilin.mybase.net.service.PokemonService
import org.json.JSONObject
import retrofit2.Call

class PokemonRequest : BaseRequest<PokemonService>() {

    override val baseUrl: String
        get() = "https://gist.githubusercontent.com/"

    override val requestBody: JSONObject
        get() = JSONObject()

    override fun getServiceClass(): Class<PokemonService> {
        return PokemonService::class.java
    }

    override suspend fun getServiceMethod(clazz: PokemonService): Call<String> {
        return clazz.getPokemonList()
    }
}