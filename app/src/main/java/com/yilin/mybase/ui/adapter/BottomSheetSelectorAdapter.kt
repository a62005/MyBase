package com.yilin.mybase.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yilin.common.ui.adapter.BaseBindingAdapter
import com.yilin.mybase.databinding.ItemBottomSheetSelectorContentBinding
import com.yilin.mybase.ui.compare.StringCompare
import com.yilin.common.ui.viewholder.BaseVBViewHolder

class BottomSheetSelectorAdapter(onItemClickListener: OnItemClickListener) :
    BaseBindingAdapter<String, BaseVBViewHolder, ItemBottomSheetSelectorContentBinding>(
        StringCompare(),
        onItemClickListener
    ) {

    override fun createViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemBottomSheetSelectorContentBinding {
        return ItemBottomSheetSelectorContentBinding.inflate(inflater, parent, false)
    }

    override fun convertPlus(
        holder: BaseVBViewHolder,
        binding: ItemBottomSheetSelectorContentBinding,
        item: String
    ) {
        binding.tvTitle.text = item
        if (holder.adapterPosition == currentList.size - 1) {
            binding.bottomLine.visibility = View.INVISIBLE
        }
    }

    override fun createViewHolder(
        binding: ItemBottomSheetSelectorContentBinding,
        viewType: Int
    ): BaseVBViewHolder {
        return getBaseViewHolder(binding)
    }
}