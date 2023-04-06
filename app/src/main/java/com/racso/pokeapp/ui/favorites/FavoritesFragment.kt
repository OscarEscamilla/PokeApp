package com.racso.pokeapp.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.racso.pokeapp.R
import com.racso.pokeapp.core.Resource
import com.racso.pokeapp.core.hide
import com.racso.pokeapp.core.show
import com.racso.pokeapp.data.model.Pokemon
import com.racso.pokeapp.databinding.FragmentFavoritesBinding
import com.racso.pokeapp.ui.pokemons.PokemonsAdapter
import com.racso.pokeapp.ui.pokemons.PokemonsFragmentDirections
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavoritesFragment : Fragment(), PokemonsAdapter.OnPokemonClickListener{

    lateinit var binding: FragmentFavoritesBinding
    private val viewModel by viewModels<FavoritesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    fun setupObservers(){
        viewModel.fetchFavorites.observe(viewLifecycleOwner){ result ->
            when(result){
                is Resource.Loading -> {
                    binding.mgError.root.hide()
                    binding.progressBar.show()
                }
                is Resource.Succes -> {
                    if (result.data.isNotEmpty()){
                        binding.mgError.root.hide()
                        binding.progressBar.hide()
                        setupFavoritePokemonRecycler(result.data)
                    }else{
                        showEmptyFavoritesView()
                    }
                }
                is Resource.Failure -> {
                    binding.progressBar.hide()
                    binding.mgError.root.show()
                }
            }
        }
    }

    fun setupFavoritePokemonRecycler(pokemonList: List<Pokemon>){
        binding.mgError.root.hide()
        binding.progressBar.hide()
        val gridLayoutManager = GridLayoutManager(context,3)
        val adapter = PokemonsAdapter(this)
        binding.rvPokemon.layoutManager = gridLayoutManager
        binding.rvPokemon.adapter = adapter
        adapter.addItems(pokemonList)
    }

    fun showEmptyFavoritesView(){
        binding.progressBar.hide()
        binding.mgError.txtMessage.text = "Añade tus pokemon favoritos :)"
        binding.mgError.txtMessage.setTextColor(ContextCompat.getColor(requireContext(), R.color.teal_700))
        binding.mgError.btnRetry.hide()
        binding.mgError.root.show()
    }

    override fun onClick(pokemon: Pokemon) {
        val action = FavoritesFragmentDirections.actionFavoritesFragmentToPokemonDetailFragment(pokemon.name)
        findNavController().navigate(action)
    }


}