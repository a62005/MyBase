package com.yilin.mybase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yilin.mybase.bean.PokemonBean
import com.yilin.mybase.bean.message.PokemonMessageBean
import com.yilin.mybase.viewmodel.respository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    private val _onPokemonTypeListListener = MutableLiveData<List<String>>()
    val onPokemonTypeListListener: LiveData<List<String>> get() = _onPokemonTypeListListener

    private val _onPokemonListListener = MutableLiveData<List<PokemonBean>>()
    val onPokemonListListener: LiveData<List<PokemonBean>> get() = _onPokemonListListener

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

    fun addFavorite(name: String) {
        repository.updatePokemonFavorite(name, true)
        repository.addMessage(
            PokemonMessageBean(
                PokemonMessageBean.PokemonMessageEnum.ADD_FAVORITE,
                name
            )
        )
    }

    fun removeFavorite(name: String) {
        repository.updatePokemonFavorite(name, false)
        repository.addMessage(
            PokemonMessageBean(
                PokemonMessageBean.PokemonMessageEnum.REMOVE_FAVORITE,
                name
            )
        )
    }
}