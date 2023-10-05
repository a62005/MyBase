package com.yilin.mybase.ui.viewholder

import com.yilin.mybase.bean.PokemonBean
import com.yilin.mybase.databinding.ItemPokemonTypeBinding
import com.yilin.mybase.ui.adapter.PokemonListAdapter

class PokemonTypeViewHolder(private val mBinding: ItemPokemonTypeBinding): BaseVBViewHolder(mBinding) {

    fun setAdapter(data: List<PokemonBean>) {
        val adapter = PokemonListAdapter()
        mBinding.rvPokemon.adapter = adapter
        adapter.submitList(data)
    }

    fun clearAdapter() {
        mBinding.rvPokemon.adapter = null
    }
}