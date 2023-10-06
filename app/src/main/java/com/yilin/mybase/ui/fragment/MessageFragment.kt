package com.yilin.mybase.ui.fragment

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.SimpleItemAnimator
import com.yilin.mybase.R
import com.yilin.mybase.databinding.FragmentMessageBinding
import com.yilin.mybase.ui.adapter.BaseListAdapter
import com.yilin.mybase.ui.adapter.MessageAdapter
import com.yilin.mybase.view.BottomSheetSelector
import com.yilin.mybase.view.callback.ItemTouchCallback
import com.yilin.mybase.viewmodel.MessageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MessageFragment : BaseFragment<FragmentMessageBinding>() {

    private val messageViewModel: MessageViewModel by lazy { getViewModel(this) }
    private val itemTouchCallback: ItemTouchCallback by lazy { ItemTouchCallback() }

    override fun getViewBinding(): FragmentMessageBinding {
        return FragmentMessageBinding.inflate(layoutInflater)
    }

    override fun initLayoutView() {
        binding.rvMessage.itemAnimator?.let {
            (it as SimpleItemAnimator).supportsChangeAnimations = false // 防止更新畫面閃爍
        }
    }

    override fun initOnClick() {
        binding.actionBar.setOnActionUpListener {
            findNavController().navigateUp()
        }
        binding.actionBar.setOnSuffixClickListener {
            val items = resources.getStringArray(R.array.message_more_item).toList()
            BottomSheetSelector.Builder
                .setContent(items)
                .setItemClickListener {
                    if (it == items.first()) {
                        messageViewModel.readAll()
                    } else {
                        messageViewModel.deleteAll()
                    }
                }.build(childFragmentManager)
        }
    }

    override fun initViewData() {
        val onItemClickListener = object : BaseListAdapter.OnItemClickListener {
            override fun onItemClick(adapter: BaseListAdapter<*, *>, v: View, index: Int) {
                if (adapter is MessageAdapter) {
                    val itemId = adapter.currentList[index].id
                    val id = v.id
                    if (id == R.id.tv_delete) {
                        messageViewModel.remove(itemId)
                    } else {
                        messageViewModel.read(itemId)
                    }
                }
            }
        }
        val messageAdapter = MessageAdapter(onItemClickListener)
        binding.rvMessage.adapter = messageAdapter
        messageViewModel.onMessageListListener.observe(viewLifecycleOwner) {
            messageAdapter.submitList(it)
            setEmptyView(it.isEmpty())
        }
        itemTouchCallback.setSlideLayout(R.id.tv_delete)
        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvMessage)
    }

    private fun setEmptyView(isEmpty: Boolean) {
        if (isEmpty) {
            binding.tvEmpty.visibility = View.VISIBLE
            binding.actionBar.hideSuffix()
        } else {
            binding.tvEmpty.visibility = View.GONE
            binding.actionBar.showSuffix()
        }
    }
}