package com.yilin.mybase.ui.fragment

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.forEach
import androidx.core.view.forEachIndexed
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.yilin.mybase.bean.PageResourceBean
import com.yilin.mybase.databinding.FragmentMainBinding
import com.yilin.mybase.ui.adapter.PageAdapter
import com.yilin.mybase.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>() {

    private val mainViewModel: MainViewModel by lazy { getViewModel(this) }

    override fun getViewBinding(): FragmentMainBinding {
        return FragmentMainBinding.inflate(layoutInflater)
    }

    override fun initLayoutView() {
        binding.vpMain.isUserInputEnabled = false
    }

    override fun initViewData() {
        initPage()
        initBNV()
    }

    private fun initPage() {
        val mainPageResources = mutableListOf<PageResourceBean>().apply {
            add(PageResourceBean { HomeFragment.newInstance() })
            add(PageResourceBean { UserFragment.newInstance() })
        }
        val pageAdapter = PageAdapter(
            childFragmentManager,
            viewLifecycleOwner.lifecycle
        )
        pageAdapter.setPageResource(mainPageResources)
        binding.vpMain.adapter = pageAdapter
    }

    private fun initBNV() {
        binding.bnv.setOnItemSelectedListener {
            binding.vpMain.currentItem = getMenuPosition(it.itemId)
            true
        }
        binding.bnv.setOnItemReselectedListener {
            scrollToTop()
        }
        val profilePagePosition = binding.bnv.menu.size() - 1
        val itemId = binding.bnv.menu.getItem(profilePagePosition).itemId
        mainViewModel.onMessageListener.observe(viewLifecycleOwner) { count ->
            if (count == 0) {
                binding.bnv.removeBadge(itemId)
            } else {
                binding.bnv.getOrCreateBadge(itemId).number = count
            }
        }
    }

    private fun scrollToTop() {
        binding.vpMain.findCurrentFragment(childFragmentManager)?.let { fragment ->
            fragment.view?.let { view ->
                if (view is NestedScrollView) {
                    view.smoothScrollTo(0, 0)
                } else if (view is ConstraintLayout) {
                    view.forEach { v ->
                        if (v is RecyclerView) {
                            v.smoothScrollToPosition(0)
                            return
                        }
                    }
                }
            }
        }
    }

    private fun getMenuPosition(id: Int): Int {
        binding.bnv.menu.forEachIndexed { index, item ->
            if (item.itemId == id) {
                return index
            }
        }
        return 0
    }
}

fun ViewPager2.findCurrentFragment(fragmentManager: FragmentManager): Fragment? {
    return fragmentManager.findFragmentByTag("f$currentItem")
}