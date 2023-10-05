package com.yilin.mybase.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.yilin.mybase.bean.CalendarNoteBean
import com.yilin.mybase.bean.message.MessageBean
import com.yilin.mybase.bean.PokemonBean

@Dao
interface MainRoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pokemonList: List<PokemonBean>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: MessageBean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: CalendarNoteBean): Long

    @Query("SELECT * FROM pokemon ORDER BY id")
    fun getPokemonList(): List<PokemonBean>

    @Query("UPDATE pokemon SET isFavorite = :isFavorite WHERE name LIKE :name")
    fun updatePokemonFavorite(name: String, isFavorite: Boolean)

    @Query("SELECT COUNT (*) AS num FROM message_table COALESCE WHERE NOT isRead")
    fun loadMessageUnreadCount(): LiveData<Int>

    @Query("SELECT * FROM message_table ORDER BY id")
    fun getMessageList(): List<MessageBean>

    @Query("SELECT * FROM note_table WHERE id LIKE :id")
    fun getCalendarNote(id: Int): CalendarNoteBean?

    @Query("SELECT * FROM note_table WHERE dateTime LIKE :dateTime")
    fun loadCalendarNoteList(dateTime: Long): LiveData<List<CalendarNoteBean>>

    @Query("SELECT DISTINCT dateTime FROM note_table")
    fun loadCalendarNoteDateList(): LiveData<List<Long>>

    @Update
    fun update(item: CalendarNoteBean)

    @Query("DELETE FROM note_table WHERE id LIKE :id")
    fun delete(id: Int)
}