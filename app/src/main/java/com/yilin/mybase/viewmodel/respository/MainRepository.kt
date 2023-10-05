package com.yilin.mybase.viewmodel.respository

import com.yilin.mybase.bean.CalendarNoteBean
import com.yilin.mybase.bean.message.MessageBean
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(
    private val ioScope: CoroutineScope,
    private val mainLocalSource: MainLocalSource,
    private val mainRemoteSource: MainRemoteSource
) {

    fun addMessage(messageBean: MessageBean) {
        ioScope.launch {
            mainLocalSource.insert(messageBean)
        }
    }

    suspend fun getPokemonList() = withContext(ioScope.coroutineContext) {
        val data = mainLocalSource.getPokemonList()
        data.ifEmpty {
            val resp = mainRemoteSource.getPokemonList()
            resp.pokemonList.apply {
                mainLocalSource.insert(this)
            }
        }
    }

    fun updatePokemonFavorite(name: String, isFavorite: Boolean) {
        ioScope.launch {
            mainLocalSource.updatePokemonFavorite(name, isFavorite)
        }
    }

    suspend fun getMessageList() = withContext(ioScope.coroutineContext) {
        mainLocalSource.getMessageList()
    }

    fun loadMessageUnreadCount() = mainLocalSource.loadMessageUnreadCount()

    fun addNote(note: CalendarNoteBean) {
        ioScope.launch {
            mainLocalSource.insert(note)
        }
    }

    fun loadCalendarNoteDateList() = mainLocalSource.loadCalendarNoteDateList()

    fun loadCalendarNoteList(dateTime: Long) = mainLocalSource.loadCalendarNoteList(dateTime)

    fun deleteCalendarNote(noteId: Int) {
        ioScope.launch {
            mainLocalSource.delete(noteId)
        }
    }

    suspend fun getCalendarNote(id: Int) = withContext(ioScope.coroutineContext) {
        mainLocalSource.getCalendarNote(id)
    }

    fun update(item: CalendarNoteBean) {
        ioScope.launch {
            mainLocalSource.update(item)
        }
    }

    fun update(item: MessageBean) {
        ioScope.launch {
            mainLocalSource.update(item)
        }
    }
}

