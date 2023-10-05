package com.yilin.mybase.ui.viewholder

import com.yilin.mybase.bean.PokemonBean
import com.yilin.mybase.databinding.ItemPokemonBinding

class PokemonViewHolder(private val mBinding: ItemPokemonBinding) : BaseVBViewHolder(mBinding) {

    fun init(item: PokemonBean) {
        mBinding.tvId.text = item.id
        mBinding.tvName.text = item.name
    }
}