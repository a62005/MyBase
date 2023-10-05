package com.yilin.mybase.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yilin.mybase.bean.message.MessageBean
import com.yilin.mybase.bean.PokemonBean

@Dao
interface MainRoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pokemonList: List<PokemonBean>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: MessageBean)

    @Query("SELECT * FROM pokemon ORDER BY id")
    fun getPokemonList(): List<PokemonBean>

    @Query("SELECT COUNT (*) AS num FROM message_table COALESCE WHERE NOT isRead")
    fun loadMessageUnreadCount(): LiveData<Int>

    @Query("SELECT * FROM message_table ORDER BY id")
    fun getMessageList(): List<MessageBean>
}