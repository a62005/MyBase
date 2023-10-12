package com.yilin.mybase.ui.fragment

import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.SimpleItemAnimator
import com.bumptech.glide.Glide
import com.yilin.common.ui.fragment.BaseFragment
import com.yilin.mybase.R
import com.yilin.mybase.bean.PokemonBean
import com.yilin.mybase.databinding.FragmentPokemonDetailBinding
import com.yilin.mybase.ui.adapter.PokemonCategoryAdapter
import com.yilin.mybase.utils.ColorUtils
import com.yilin.mybase.viewmodel.PokemonDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonDetailFragment : BaseFragment<FragmentPokemonDetailBinding>() {

    private val pokemonViewModel: PokemonDetailViewModel by lazy { getViewModel(this) }
    private lateinit var pokemon: PokemonBean
    private lateinit var categoryAdapter: PokemonCategoryAdapter

    override fun getViewBinding(): FragmentPokemonDetailBinding {
        return FragmentPokemonDetailBinding.inflate(layoutInflater)
    }

    override fun initLayoutView() {
        initCategory()
    }

    override fun initOnClick() {
        binding.actionBar.setOnActionUpListener {
            findNavController().navigateUp()
        }
        binding.btnNext.setOnClickListener {
            pokemonViewModel.setNextPokemon()
        }
        binding.btnPrevious.setOnClickListener {
            pokemonViewModel.setPreviousPokemon()
        }
        binding.ivHeart.setOnClickListener {
            val isFavorite = !pokemon.isFavorite
            pokemon.isFavorite = isFavorite
            setFavorite(isFavorite)
            pokemonViewModel.setFavorite(pokemon.name, isFavorite)
        }
    }

    override fun initViewData() {
        binding.viewModel = pokemonViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val id = navArgs<PokemonDetailFragmentArgs>().value.id
        pokemonViewModel.setPokemon(id)

        pokemonViewModel.onPokemonListener.observe(viewLifecycleOwner) {
            pokemon = it
            setPokemon(it)
        }
    }

    private fun initCategory() {
        binding.rvCategory.itemAnimator?.let {
            (it as SimpleItemAnimator).supportsChangeAnimations = false // 防止更新畫面閃爍
        }
        categoryAdapter = PokemonCategoryAdapter()
        binding.rvCategory.adapter = categoryAdapter
    }

    private fun setPokemon(pokemon: PokemonBean) {
        Glide.with(requireContext()).load(pokemon.imageUrl).into(binding.ivPokemon)
        binding.clTop.setBackgroundColor(ColorUtils.getColor(pokemon.type))
        binding.actionBar.setBackgroundColor(ColorUtils.getColor(pokemon.type))
        setFavorite(pokemon.isFavorite)

        categoryAdapter.submitList(pokemon.types)
    }

    private fun setFavorite(isFavorite: Boolean) {
        val res = if (isFavorite) {
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_heart)
        } else {
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_heart_disable)
        }
        binding.ivHeart.setImageDrawable(res)
    }
}