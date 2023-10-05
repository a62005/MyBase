package com.yilin.mybase.ui.fragment

import com.yilin.mybase.databinding.FragmentMessageBinding
import com.yilin.mybase.ui.adapter.MessageAdapter
import com.yilin.mybase.viewmodel.MessageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MessageFragment : BaseFragment<FragmentMessageBinding>() {

    private val messageViewModel: MessageViewModel by lazy { getViewModel(this) }

    override fun getViewBinding(): FragmentMessageBinding {
        return FragmentMessageBinding.inflate(layoutInflater)
    }

    override fun initLayoutView() {

    }

    override fun initViewData() {
        val messageAdapter = MessageAdapter()
        binding.rvMessage.adapter = messageAdapter
        messageViewModel.onMessageListListener.observe(viewLifecycleOwner) {
            messageAdapter.submitList(it)
        }
    }
}