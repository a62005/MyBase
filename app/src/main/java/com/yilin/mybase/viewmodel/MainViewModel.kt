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
class MainViewModel @Inject constructor(private val repository: MainRepository): ViewModel() {

    private val _onPokemonListListener = MutableLiveData<List<PokemonBean>>()
    val onPokemonListListener: LiveData<List<PokemonBean>> get() = _onPokemonListListener

    init {
        viewModelScope.launch {
            _onPokemonListListener.value = repository.getPokemonList()
        }
    }
}