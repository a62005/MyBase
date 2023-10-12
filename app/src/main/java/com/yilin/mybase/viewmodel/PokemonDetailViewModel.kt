package com.yilin.mybase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yilin.mybase.bean.PokemonBean
import com.yilin.mybase.viewmodel.respository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(private val repository: MainRepository) :
    ViewModel() {

    private val _onPokemonListener = MutableLiveData<PokemonBean>()
    val onPokemonListener: LiveData<PokemonBean> get() = _onPokemonListener
    private var maxCount = 1
    private val minCount = 1
    private var currentId = ""

    init {
        viewModelScope.launch {
            maxCount = repository.getPokemonCount()
        }
    }

    fun setFavorite(name: String, isFavorite: Boolean) {
        repository.updatePokemonFavorite(name, isFavorite)
    }

    fun setPokemon(id: String) {
        currentId = id
        viewModelScope.launch {
            _onPokemonListener.value = repository.getPokemonById(id)
        }
    }

    fun setNextPokemon() {
        var id = currentId.removePrefix("#").toInt()
        if (++id > maxCount) {
            id = minCount
        }
        setPokemon(getFormatId(id))
    }

    fun setPreviousPokemon() {
        var id = currentId.removePrefix("#").toInt()
        if (--id < minCount) {
            id = maxCount
        }
        setPokemon(getFormatId(id))
    }

    private fun getFormatId(id: Int): String {
        val length = maxCount.toString().length - id.toString().length
        var result = "#"
        repeat(length) {
            result += "0"
        }
        return "$result$id"
    }
}