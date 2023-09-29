package com.yilin.mybase.ui.fragment

import com.yilin.mybase.databinding.FragmentMainBinding
import com.yilin.mybase.ui.adapter.PokemonListAdapter
import com.yilin.mybase.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>() {

    private val mainViewModel: MainViewModel by lazy { getViewModel(this) }

    override fun getViewBinding(): FragmentMainBinding {
        return FragmentMainBinding.inflate(layoutInflater)
    }

    override fun initLayoutView() {
    }

    override fun initViewData() {
        binding.viewModel = mainViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        val adapter = PokemonListAdapter()
        binding.rvPokemon.adapter = adapter
        mainViewModel.onPokemonListListener.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}