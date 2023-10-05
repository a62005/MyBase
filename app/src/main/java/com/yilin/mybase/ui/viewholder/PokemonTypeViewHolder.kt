package com.yilin.mybase.ui.viewholder

import androidx.recyclerview.widget.GridLayoutManager
import com.yilin.mybase.bean.PokemonBean
import com.yilin.mybase.databinding.ItemPokemonTypeBinding
import com.yilin.mybase.ui.adapter.PokemonListAdapter
import com.yilin.mybase.utils.ColorUtils
import com.yilin.mybase.view.decoration.ItemDecoration

class PokemonTypeViewHolder(
    private val mBinding: ItemPokemonTypeBinding,
    private val onPokemonClickListener: PokemonListAdapter.OnPokemonClickListener
) : BaseVBViewHolder(mBinding) {

    fun setType(type: String) {
        mBinding.btnType.text = type
        mBinding.btnType.setBackgroundColor(ColorUtils.getColor(type))
        setDecoration()
    }

    private fun setDecoration() {
        mBinding.rvPokemon.layoutManager?.let { manager ->
            if (manager is GridLayoutManager) {
                mBinding.rvPokemon.addItemDecoration(ItemDecoration(manager.spanCount, 16))
            }
        }

    }

    fun setAdapter(data: List<PokemonBean>) {
        val adapter = PokemonListAdapter(onPokemonClickListener)
        mBinding.rvPokemon.adapter = adapter
        adapter.submitList(data)
    }

    fun clearAdapter() {
        mBinding.rvPokemon.adapter = null
    }
}