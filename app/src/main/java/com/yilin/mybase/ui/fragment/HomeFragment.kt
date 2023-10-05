package com.yilin.mybase.ui.fragment

import android.view.View
import com.yilin.mybase.databinding.FragmentHomeBinding
import com.yilin.mybase.ui.adapter.BaseListAdapter
import com.yilin.mybase.ui.adapter.PokemonTypeAdapter
import com.yilin.mybase.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment private constructor() : BaseFragment<FragmentHomeBinding>() {

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    private val homeViewModel: HomeViewModel by lazy { getViewModel(this) }

    override fun getViewBinding(): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun initLayoutView() {
    }

    override fun initViewData() {
        binding.viewModel = homeViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        val onItemClickListener = object : BaseListAdapter.OnItemClickListener {
            override fun onItemClick(adapter: BaseListAdapter<*, *>, v: View, index: Int) {
                if (adapter is PokemonTypeAdapter) {
                    val type = adapter.currentList[index]
                    homeViewModel.setPokemonListByType(type)
                }
            }
        }
        val adapter = PokemonTypeAdapter(onItemClickListener)
        binding.rvPokemonType.adapter = adapter
        homeViewModel.onPokemonTypeListListener.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        homeViewModel.onPokemonListListener.observe(viewLifecycleOwner) {
            adapter.setPokemonList(it)
        }
    }
}