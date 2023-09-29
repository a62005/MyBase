package com.yilin.mybase.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yilin.mybase.bean.PokemonBean
import com.yilin.mybase.databinding.ItemPokemonBinding
import com.yilin.mybase.ui.compare.PokemonCompare
import com.yilin.mybase.ui.viewholder.PokemonViewHolder

class PokemonListAdapter: BaseBindingAdapter<PokemonBean, PokemonViewHolder, ItemPokemonBinding>(
    PokemonCompare()
) {
    override fun convertPlus(
        holder: PokemonViewHolder,
        binding: ItemPokemonBinding,
        item: PokemonBean
    ) {
        holder.init(item)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemPokemonBinding {
        return ItemPokemonBinding.inflate(inflater, parent, false)
    }

    override fun createViewHolder(binding: ItemPokemonBinding, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder(binding)
    }
}