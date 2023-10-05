package com.yilin.mybase.ui.compare

import androidx.recyclerview.widget.DiffUtil
import com.yilin.mybase.bean.PokemonBean

class PokemonCompare : DiffUtil.ItemCallback<PokemonBean>() {

    override fun areItemsTheSame(oldItem: PokemonBean, newItem: PokemonBean): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PokemonBean, newItem: PokemonBean): Boolean {
        return oldItem.name == newItem.name
    }
}