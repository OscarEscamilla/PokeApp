package com.racso.pokeapp.ui.pokemons

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.racso.pokeapp.R
import com.racso.pokeapp.core.Resource
import com.racso.pokeapp.core.toast
import com.racso.pokeapp.databinding.FragmentPokemonDetailBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PokemonDetailFragment : Fragment() {

    lateinit var binding: FragmentPokemonDetailBinding
    val args: PokemonDetailFragmentArgs by navArgs()
    val viewModel: PokemonsViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokemonDetailBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.toast(args.pokemonName)
        setupObservers()
        viewModel.getPokemonById(args.pokemonName)
    }

    fun setupObservers() {
        viewModel.pokemon.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> {}
                is Resource.Succes -> {
                    context?.toast("sucesss")
                    Log.d("POKEMON", it.data.toString())
                }
                is Resource.Failure -> {}
            }
        }
    }

}