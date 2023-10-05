package com.yilin.mybase.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yilin.mybase.bean.PageResourceBean

class PageAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(
    fragmentManager, lifecycle
) {

    private lateinit var mainPageResources: List<PageResourceBean>

    fun setPageResource(res: List<PageResourceBean>) {
        mainPageResources = res
    }

    override fun createFragment(position: Int): Fragment {
        return mainPageResources[position].getFragment!!.invoke()
    }

    override fun getItemCount(): Int {
        return mainPageResources.size
    }

}