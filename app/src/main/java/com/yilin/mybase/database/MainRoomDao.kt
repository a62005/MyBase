package com.yilin.mybase.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yilin.mybase.bean.PokemonBean

@Dao
interface MainRoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pokemonList: List<PokemonBean>)

    @Query("SELECT * FROM pokemon ORDER BY id")
    fun getPokemonList(): List<PokemonBean>
}