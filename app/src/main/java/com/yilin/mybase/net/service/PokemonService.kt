package com.yilin.mybase.net.service

import retrofit2.Call
import retrofit2.http.GET

interface PokemonService {

    @GET
    fun getPokemonList() : Call<String>

}