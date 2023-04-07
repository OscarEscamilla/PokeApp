package com.racso.pokeapp.ui.pokemons

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.racso.mylocations.ui.LocationsActivity
import com.racso.pokeapp.core.*
import com.racso.pokeapp.data.model.Pokemon
import com.racso.pokeapp.databinding.FragmentPokemonsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonsFragment : Fragment(), PokemonsAdapter.OnPokemonClickListener{

    private lateinit var binding: FragmentPokemonsBinding
    private val viewModel: PokemonsViewModel by viewModels()

    private val lastVisibleItemPosition: Int
        get() = grindLayoutManager.findLastVisibleItemPosition()

    lateinit var grindLayoutManager: GridLayoutManager
    lateinit var adapter: PokemonsAdapter
    private lateinit var scrollListener: RecyclerView.OnScrollListener


    var offset: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getPokemonsList(offset = offset)
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
        setupRecyclerView()
        setupObservers()
        setupListeners()
    }

    fun setupRecyclerView(){
        grindLayoutManager = GridLayoutManager(context,3)
        adapter = PokemonsAdapter(this)
        binding.rvPokemon.layoutManager = grindLayoutManager
        binding.rvPokemon.adapter = adapter
    }

    fun setupObservers(){
       viewModel.pokemonsList.observe(viewLifecycleOwner){ result ->
           when(result){
               is Resource.Loading -> {
                   binding.mgError.root.hide()
                    binding.progressBar.show()
               }
               is Resource.Succes -> {
                   if (result.data.isNotEmpty()){
                       binding.mgError.root.hide()
                       binding.progressBar.hide()
                       adapter.addItems(result.data)
                   }
               }
               is Resource.Failure -> {
                   binding.progressBar.hide()
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

        binding.floatingActionButton.setOnClickListener {
            startActivity(Intent(requireContext(), LocationsActivity::class.java))
        }

        scrollListener = object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = recyclerView.layoutManager?.itemCount
                if (totalItemCount == lastVisibleItemPosition + 1) {
                    offset+=20
                    viewModel.getPokemonsList(offset)
                }
            }
        }
        binding.rvPokemon.addOnScrollListener(scrollListener)

    }

    override fun onClick(pokemon: Pokemon) {
        val action = PokemonsFragmentDirections.actionPokemonsFragmentToPokemonDetailFragment(pokemon.name)
        findNavController().navigate(action)
    }

}