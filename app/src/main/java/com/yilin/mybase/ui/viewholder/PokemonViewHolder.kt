package com.yilin.mybase.ui.viewholder

import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.yilin.mybase.R
import com.yilin.mybase.bean.PokemonBean
import com.yilin.mybase.databinding.ItemPokemonBinding
import com.yilin.mybase.utils.ColorUtils

class PokemonViewHolder(private val mBinding: ItemPokemonBinding) : BaseVBViewHolder(mBinding) {

    fun init(item: PokemonBean) {
        mBinding.tvId.text = item.id
        mBinding.tvName.text = item.name
        mBinding.tvAtk.text = getString(R.string.pokemon_set_atk).format(item.attack)
        mBinding.tvDef.text = getString(R.string.pokemon_set_def).format(item.defense)
        mBinding.tvSpd.text = getString(R.string.pokemon_set_spd).format(item.speed)
        Glide.with(itemView.context).load(item.imageUrl).into(mBinding.ivPokemon)
        mBinding.root.setBackgroundColor(ColorUtils.getColor(item.type))
        setFavorite(item.isFavorite)
    }

    fun setFavorite(isFavorite: Boolean) {
        val res = if (isFavorite) {
            ContextCompat.getDrawable(itemView.context, R.drawable.ic_heart)
        } else {
            ContextCompat.getDrawable(itemView.context, R.drawable.ic_heart_disable)
        }
        mBinding.ivHeart.setImageDrawable(res)
    }
}