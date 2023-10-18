package com.yilin.mybase.net.service

import com.yilin.common.net.service.BaseService
import com.yilin.mybase.bean.PokemonBean
import retrofit2.Call
import retrofit2.http.GET

interface PokemonService : BaseService {

    @GET("mrcsxsiq/b94dbe9ab67147b642baa9109ce16e44/raw/97811a5df2df7304b5bc4fbb9ee018a0339b8a38")
    fun getPokemonList(): Call<List<PokemonBean>>
}