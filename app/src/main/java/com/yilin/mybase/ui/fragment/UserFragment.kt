package com.yilin.mybase.ui.fragment

import androidx.navigation.fragment.findNavController
import com.yilin.mybase.databinding.FragmentUserBinding

class UserFragment private constructor(): BaseFragment<FragmentUserBinding>() {

    companion object {
        fun newInstance(): UserFragment {
            return UserFragment()
        }
    }

    override fun getViewBinding(): FragmentUserBinding {
        return FragmentUserBinding.inflate(layoutInflater)
    }

    override fun initLayoutView() {
    }

    override fun initOnClick() {
        binding.itemMessage.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToMessageFragment())
        }
        binding.itemCalendar.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToCalendarActivity())
        }
    }

    override fun initViewData() {
    }
}