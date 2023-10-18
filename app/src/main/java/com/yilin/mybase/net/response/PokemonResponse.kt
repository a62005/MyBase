package com.yilin.mybase.net.response

import com.google.gson.reflect.TypeToken
import com.yilin.common.net.response.BaseResponse
import com.yilin.mybase.MyApp
import com.yilin.mybase.bean.PokemonBean

class PokemonResponse(response: BaseResponse<String>) : BaseResponse<String>(response) {

    val pokemonList: List<PokemonBean>
        get() {
            val type = object : TypeToken<List<PokemonBean>>() {}.type
            return MyApp.instance.gson.fromJson(data, type)
        }
}