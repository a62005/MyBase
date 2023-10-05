package com.yilin.mybase.ui.fragment

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.yilin.mybase.databinding.FragmentPokemonDetailBinding
import com.yilin.mybase.viewmodel.PokemonDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonDetailFragment : BaseFragment<FragmentPokemonDetailBinding>() {

    private val pokemonViewModel: PokemonDetailViewModel by lazy { getViewModel(this) }

    override fun getViewBinding(): FragmentPokemonDetailBinding {
        return FragmentPokemonDetailBinding.inflate(layoutInflater)
    }

    override fun initLayoutView() {
    }

    override fun initOnClick() {
        binding.actionBar.setOnActionUpListener {
            findNavController().navigateUp()
        }
    }

    override fun initViewData() {
        val id = navArgs<PokemonDetailFragmentArgs>().value.id
        pokemonViewModel.setPokemon(id)

        pokemonViewModel.onPokemonListener.observe(viewLifecycleOwner) {
            binding.tvId.text = it.id
        }
    }
}