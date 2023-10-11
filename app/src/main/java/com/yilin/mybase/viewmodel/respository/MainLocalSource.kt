package com.yilin.mybase.viewmodel.respository

import com.google.gson.Gson
import com.yilin.mybase.MyApp
import com.yilin.mybase.bean.CalendarNoteBean
import com.yilin.mybase.bean.PokemonBean
import com.yilin.mybase.bean.MessageBean
import com.yilin.mybase.database.MainRoomDao
import com.yilin.mybase.manager.SPManager
import javax.inject.Singleton

@Singleton
class MainLocalSource(private val mainDao: MainRoomDao) {

    private val spManager: SPManager by lazy { SPManager.instance }

    private val gson: Gson by lazy { MyApp.instance.gson }

    fun insert(pokemonList: List<PokemonBean>) {
        pokemonList.onEach {
            it.type = it.types.first()
        }.apply {
            mainDao.insert(this)
        }
    }

    fun insert(item: MessageBean) {
        mainDao.insert(item)
    }

    fun insert(item: CalendarNoteBean): Long {
        return mainDao.insert(item)
    }

    fun getPokemonTypeList() = mainDao.getPokemonTypeList()

    fun getPokemonListByType(type: String) = mainDao.getPokemonListByType(type)

    fun getPokemonById(id: String) = mainDao.getPokemonById(id)

    fun getPokemonCount() = mainDao.getPokemonCount()

    fun updatePokemonFavorite(name: String, isFavorite: Boolean) =
        mainDao.updatePokemonFavorite(name, isFavorite)

    fun loadMessageUnreadCount() = mainDao.loadMessageUnreadCount()

    fun getMessageList() = mainDao.getMessageList()

    fun loadMessageList() = mainDao.loadMessageList()

    fun getCalendarNote(id: Int) = mainDao.getCalendarNote(id)

    fun loadCalendarNoteDateList() = mainDao.loadCalendarNoteDateList()

    fun loadCalendarNoteList(dateTime: Long) = mainDao.loadCalendarNoteList(dateTime)

    fun update(item: CalendarNoteBean) {
        mainDao.update(item)
    }

    fun setMessageRead(id: Int) {
        mainDao.setMessageRead(id)
    }

    fun delete(id: Int) {
        mainDao.delete(id)
    }

    fun deleteMessage() {
        mainDao.deleteMessage()
    }

    fun deleteMessage(id: Int) {
        mainDao.deleteMessage(id)
    }

}