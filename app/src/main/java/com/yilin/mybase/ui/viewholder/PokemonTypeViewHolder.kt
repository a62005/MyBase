package com.yilin.mybase.ui.viewholder

import android.content.res.ColorStateList
import androidx.recyclerview.widget.GridLayoutManager
import com.yilin.common.ui.viewholder.BaseVBViewHolder
import com.yilin.mybase.bean.PokemonItemBean
import com.yilin.mybase.databinding.ItemPokemonTypeBinding
import com.yilin.mybase.ui.adapter.PokemonListAdapter
import com.yilin.mybase.utils.ColorUtils
import com.yilin.mybase.view.decoration.ItemDecoration

class PokemonTypeViewHolder(
    private val mBinding: ItemPokemonTypeBinding,
    private val onPokemonClickListener: PokemonListAdapter.OnPokemonClickListener
) : BaseVBViewHolder(mBinding) {

    private var itemDecoration: ItemDecoration? = null

    fun setType(type: String) {
        mBinding.btnType.text = type
        mBinding.btnType.backgroundTintList = ColorStateList.valueOf(ColorUtils.getColor(type))
    }

    private fun setDecoration() {
        mBinding.rvPokemon.layoutManager?.let { manager ->
            if (manager is GridLayoutManager) {
                ItemDecoration(manager.spanCount).apply {
                    itemDecoration = this
                    mBinding.rvPokemon.addItemDecoration(this)
                }
            }
        }
    }

    fun setAdapter(data: List<PokemonItemBean>) {
        setDecoration()
        val adapter = PokemonListAdapter(onPokemonClickListener)
        mBinding.rvPokemon.adapter = adapter
        adapter.submitList(data)
    }

    fun clearAdapter() {
        itemDecoration?.let {
            mBinding.rvPokemon.removeItemDecoration(it)
        }
        mBinding.rvPokemon.adapter = null
    }
}