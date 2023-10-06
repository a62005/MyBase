package com.yilin.mybase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yilin.mybase.bean.PokemonItemBean
import com.yilin.mybase.factory.MessageFactory
import com.yilin.mybase.viewmodel.respository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    private val _onPokemonTypeListListener = MutableLiveData<List<String>>()
    val onPokemonTypeListListener: LiveData<List<String>> get() = _onPokemonTypeListListener

    private val _onPokemonListListener = MutableLiveData<List<PokemonItemBean>>()
    val onPokemonListListener: LiveData<List<PokemonItemBean>> get() = _onPokemonListListener

    init {
        viewModelScope.launch {
            _onPokemonTypeListListener.value = repository.getPokemonTypeList()
        }
    }

    fun setPokemonListByType(type: String) {
        viewModelScope.launch {
            _onPokemonListListener.value = repository.getPokemonListByType(type)
        }
    }

    fun addFavorite(id: String, name: String) {
        repository.updatePokemonFavorite(name, true)
        repository.addMessage(MessageFactory.PokemonMessage.ADD_FAVORITE.getMessage(id, name))
    }

    fun removeFavorite(id: String, name: String) {
        repository.updatePokemonFavorite(name, false)
        repository.addMessage(MessageFactory.PokemonMessage.REMOVE_FAVORITE.getMessage(id, name))
    }
}