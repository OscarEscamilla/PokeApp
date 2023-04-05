package com.racso.pokeapp.ui.pokemons

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.racso.pokeapp.R
import com.racso.pokeapp.core.Resource
import com.racso.pokeapp.core.hide
import com.racso.pokeapp.core.show
import com.racso.pokeapp.core.toast
import com.racso.pokeapp.data.model.PokemonEntry
import com.racso.pokeapp.databinding.FragmentPokemonsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonsFragment : Fragment(), PokemonsAdapter.OnPokemonClickListener{

    private lateinit var binding: FragmentPokemonsBinding
    private val viewModel: PokemonsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getPokemonsList(offset = 0)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokemonsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupListeners()
    }

    fun setupObservers(){
       viewModel.pokemonsList.observe(viewLifecycleOwner){ result ->
           when(result){
               is Resource.Loading -> {
                    binding.progressBar.show()
               }
               is Resource.Succes -> {
                   binding.mgError.root.hide()
                   binding.progressBar.hide()
                   val adapter = PokemonsAdapter(result.data, this)
                   binding.rvMovies.layoutManager = GridLayoutManager(context,3)
                   binding.rvMovies.adapter = adapter
               }
               is Resource.Failure -> {
                   binding.mgError.root.show()
               }
           }
       }

    }

    fun setupListeners(){
        binding.mgError.btnRetry.setOnClickListener {
            context?.toast("retry...")
            viewModel.getPokemonsList(offset = 0)
        }

    }

    override fun onClick(pokemon: PokemonEntry) {
        val action = PokemonsFragmentDirections.actionPokemonsFragmentToPokemonDetailFragment(pokemon.name)
        findNavController().navigate(action)
    }

}