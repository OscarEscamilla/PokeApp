package com.racso.pokeapp.ui.pokemons

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.racso.pokeapp.R
import com.racso.pokeapp.core.Resource
import com.racso.pokeapp.core.hide
import com.racso.pokeapp.core.show
import com.racso.pokeapp.data.model.Pokemon
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
        setupObservers()
        setupListeners()
        viewModel.getPokemonById(args.pokemonName)
    }

    fun setupObservers() {
        viewModel.pokemon.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> {}
                is Resource.Succes -> {
                    binding.mgError.root.hide()
                    binding.pokemonData.show()
                    setupView(it.data)
                }
                is Resource.Failure -> {
                    binding.mgError.txtMessage.text = getString(R.string.network_error)
                    binding.mgError.root.show()
                    binding.pokemonData.hide()
                }
            }
        }
    }

    fun setupListeners(): Unit {
        binding.mgError.btnRetry.setOnClickListener {
            viewModel.getPokemonById(args.pokemonName)
        }
    }


    fun setupView(pokemon: Pokemon){
        Glide.with(this)
            .load(pokemon.image_detail)
            .placeholder(R.drawable.pokeball)
            .error(R.drawable.pokeball)
            .into(binding.imgPokemon)
        binding.txtName.text = pokemon.name.capitalize()
        binding.txtHeight.text = "${pokemon.height} M"
        binding.txtWeight.text = "${pokemon.weight} KG"

        binding.hpSkill.progressBar.progress =  pokemon.hp
        binding.hpSkill.progressBar.progressTintList = context?.let { ColorStateList.valueOf(it.getColor(R.color.green_400)) };
        binding.attackSkill.progressBar.progress =  pokemon.attack
        binding.attackSkill.progressBar.progressTintList = context?.let { ColorStateList.valueOf(it.getColor(R.color.orange_400)) };
        binding.defenseSkill.progressBar.progress =  pokemon.defense
        binding.defenseSkill.progressBar.progressTintList = context?.let { ColorStateList.valueOf(it.getColor(R.color.blue_400)) };
        binding.specialAtack.progressBar.progress =  pokemon.special_attack
        binding.specialAtack.progressBar.progressTintList = context?.let { ColorStateList.valueOf(it.getColor(R.color.red_400)) };

    }

}