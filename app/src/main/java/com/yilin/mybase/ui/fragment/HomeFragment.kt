package com.yilin.mybase.ui.fragment

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.yilin.mybase.databinding.FragmentHomeBinding
import com.yilin.mybase.ui.adapter.BaseListAdapter
import com.yilin.mybase.ui.adapter.PokemonListAdapter
import com.yilin.mybase.ui.adapter.PokemonTypeAdapter
import com.yilin.mybase.view.BottomSheetSelector
import com.yilin.mybase.view.decoration.ItemDecoration
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
        binding.rvPokemonType.itemAnimator?.let {
            (it as SimpleItemAnimator).supportsChangeAnimations = false // 防止更新畫面閃爍
        }
    }

    private val onTypeItemClickListener: BaseListAdapter.OnItemClickListener
        get() = object : BaseListAdapter.OnItemClickListener {
            override fun onItemClick(adapter: BaseListAdapter<*, *>, v: View, index: Int) {
                binding.rvPokemonType.scrollToPosition(index)
                if (adapter is PokemonTypeAdapter) {
                    val type = adapter.currentList[index]
                    homeViewModel.setPokemonListByType(type)
                }
            }
        }

    private val onPokemonClickListener: PokemonListAdapter.OnPokemonClickListener = object : PokemonListAdapter.OnPokemonClickListener {
        override fun onFavoriteClick(name: String, isFavorite: Boolean) {
            if (isFavorite) {
                homeViewModel.addFavorite(name)
            } else {
                homeViewModel.removeFavorite(name)
            }
        }

        override fun onPokemonClick(id: String) {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToPokemonDetailFragment(id))
        }
    }

    override fun initViewData() {
        binding.viewModel = homeViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        val adapter = PokemonTypeAdapter(onTypeItemClickListener, onPokemonClickListener)
        binding.rvPokemonType.adapter = adapter
        homeViewModel.onPokemonTypeListListener.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        homeViewModel.onPokemonListListener.observe(viewLifecycleOwner) {
            adapter.setPokemonList(it)
        }
    }
}