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
class PokemonDetailViewModel @Inject constructor(private val repository: MainRepository): ViewModel() {

    private val _onPokemonListener = MutableLiveData<PokemonBean>()
    val onPokemonListener: LiveData<PokemonBean> get() = _onPokemonListener

    fun setPokemon(id: String) {
        viewModelScope.launch {
            _onPokemonListener.value = repository.getPokemonById(id)
        }
    }
}