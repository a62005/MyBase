package com.yilin.mybase.ui.compare

import androidx.recyclerview.widget.DiffUtil
import com.yilin.mybase.bean.PokemonItemBean

class PokemonCompare : DiffUtil.ItemCallback<PokemonItemBean>() {

    override fun areItemsTheSame(oldItem: PokemonItemBean, newItem: PokemonItemBean): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PokemonItemBean, newItem: PokemonItemBean): Boolean {
        return oldItem.name == newItem.name
    }
}