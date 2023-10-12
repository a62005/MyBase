package com.yilin.mybase.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yilin.mybase.databinding.ItemPokemonCategoryBinding
import com.yilin.mybase.ui.compare.StringCompare
import com.yilin.mybase.ui.viewholder.BaseVBViewHolder

class PokemonCategoryAdapter :
    BaseBindingAdapter<String, BaseVBViewHolder, ItemPokemonCategoryBinding>(
        StringCompare()
    ) {
    override fun convertPlus(
        holder: BaseVBViewHolder,
        binding: ItemPokemonCategoryBinding,
        item: String
    ) {
        binding.btnCategory.text = item
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemPokemonCategoryBinding {
        return ItemPokemonCategoryBinding.inflate(inflater, parent, false)
    }

    override fun createViewHolder(
        binding: ItemPokemonCategoryBinding,
        viewType: Int
    ): BaseVBViewHolder {
        return getBaseViewHolder(binding)
    }
}