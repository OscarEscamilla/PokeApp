package com.racso.pokeapp.ui.pokemons

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.racso.pokeapp.R
import com.racso.pokeapp.core.Resource
import com.racso.pokeapp.databinding.FragmentPokemonsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonsFragment : Fragment() {

    private lateinit var binding: FragmentPokemonsBinding
    private val viewModel: PokemonsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getPokemons(0)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokemonsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }



    fun setupObservers(){
       viewModel.pokemonsList.observe(viewLifecycleOwner){ result ->
           when(result){
               is Resource.Loading -> {

               }
               is Resource.Succes -> {

                   val adapter = PokemonsAdapter(result.data)
                   binding.rvMovies.layoutManager = GridLayoutManager(context,3)
                   binding.rvMovies.adapter = adapter

               }
               is Resource.Failure -> {

               }
           }
       }

    }

}