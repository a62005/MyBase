package com.yilin.mybase.ui.fragment

import com.yilin.mybase.databinding.FragmentPokemonListBinding
import com.yilin.mybase.ui.adapter.PokemonListAdapter
import com.yilin.mybase.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonListFragment private constructor(): BaseFragment<FragmentPokemonListBinding>() {

    companion object {
        fun newInstance(): PokemonListFragment {
            return PokemonListFragment()
        }
    }

    private val homeViewModel: HomeViewModel by lazy { getViewModel(this) }

    override fun getViewBinding(): FragmentPokemonListBinding {
        return FragmentPokemonListBinding.inflate(layoutInflater)
    }

    override fun initLayoutView() {
    }

    override fun initViewData() {
        binding.viewModel = homeViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        val adapter = PokemonListAdapter()
        binding.rvPokemon.adapter = adapter
        homeViewModel.onPokemonListListener.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}