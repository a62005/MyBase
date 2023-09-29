package com.yilin.mybase.viewmodel.respository

import com.google.gson.Gson
import com.yilin.mybase.database.MainRoomDao
import com.yilin.mybase.MyApp
import com.yilin.mybase.bean.PokemonBean
import com.yilin.mybase.manager.SPManager
import javax.inject.Singleton

@Singleton
class MainLocalSource(private val mainDao: MainRoomDao) {

    private val spManager: SPManager by lazy { SPManager.instance }

    private val gson: Gson by lazy { MyApp.instance.gson }

    fun insert(pokemonList: List<PokemonBean>) {
        mainDao.insert(pokemonList)
    }

    fun getPokemonList() = mainDao.getPokemonList()

}