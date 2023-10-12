package com.yilin.mybase.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yilin.common.ui.adapter.BaseBindingAdapter
import com.yilin.mybase.bean.CalendarNoteBean
import com.yilin.mybase.databinding.ItemCalendarNoteBinding
import com.yilin.mybase.ui.compare.CalendarNoteCompare
import com.yilin.mybase.ui.viewholder.CalendarNoteViewHolder

class CalendarNoteAdapter(
    private val mOnItemClickListener: OnItemClickListener,
    private val mOnLongClickListener: (adapter: RecyclerView.Adapter<*>, index: Int) -> Unit
) : BaseBindingAdapter<CalendarNoteBean, CalendarNoteViewHolder, ItemCalendarNoteBinding>(
    CalendarNoteCompare()
) {

    override fun convertPlus(
        holder: CalendarNoteViewHolder,
        binding: ItemCalendarNoteBinding,
        item: CalendarNoteBean
    ) {
        holder.init(item)
        binding.tvDelete.setOnClickListener {
            mOnItemClickListener.onItemClick(this, it, holder.adapterPosition)
        }
        binding.root.setOnLongClickListener {
            mOnLongClickListener.invoke(this, holder.adapterPosition)
            false
        }
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemCalendarNoteBinding {
        return ItemCalendarNoteBinding.inflate(inflater, parent, false)
    }

    override fun createViewHolder(
        binding: ItemCalendarNoteBinding,
        viewType: Int
    ): CalendarNoteViewHolder {
        return CalendarNoteViewHolder(binding)
    }
}