package com.yilin.mybase.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yilin.mybase.bean.PokemonBean
import com.yilin.mybase.databinding.ItemPokemonTypeBinding
import com.yilin.mybase.ui.compare.StringCompare
import com.yilin.mybase.ui.viewholder.PokemonTypeViewHolder

class PokemonTypeAdapter(onItemClickListener: OnItemClickListener) :
    BaseBindingAdapter<String, PokemonTypeViewHolder, ItemPokemonTypeBinding>(
        StringCompare(),
        onItemClickListener
    ) {

    private var selectedIndex = -1
    private var pokemonList = emptyList<PokemonBean>()

    override fun convertPlus(
        holder: PokemonTypeViewHolder,
        binding: ItemPokemonTypeBinding,
        item: String
    ) {
        binding.btnType.text = item
        if (holder.adapterPosition == selectedIndex) {
            holder.setAdapter(pokemonList)
        } else {
            holder.clearAdapter()
        }
        binding.btnType.setOnClickListener {
            val index = holder.adapterPosition
            if (index == selectedIndex) {
                reset(index)
            } else {
                onItemClickListener?.onItemClick(this, it, holder.adapterPosition)
            }
        }
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemPokemonTypeBinding {
        return ItemPokemonTypeBinding.inflate(inflater, parent, false)
    }

    override fun createViewHolder(
        binding: ItemPokemonTypeBinding,
        viewType: Int
    ): PokemonTypeViewHolder {
        return PokemonTypeViewHolder(binding)
    }

    fun setPokemonList(data: List<PokemonBean>) {
        pokemonList = data
        val type = data.first().type
        val index = currentList.indexOf(type)
        val lastIndex = selectedIndex
        selectedIndex = index
        if (lastIndex != -1) {
            notifyItemChanged(lastIndex)
        }
        notifyItemChanged(selectedIndex)
    }

    private fun reset(index: Int) {
        selectedIndex = -1
        notifyItemChanged(index)
    }
}