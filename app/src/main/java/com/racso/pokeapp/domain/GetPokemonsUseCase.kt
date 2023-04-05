package com.racso.pokeapp.domain

import com.racso.pokeapp.data.model.Pokemon
import com.racso.pokeapp.data.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonsUseCase @Inject constructor( private val repository: PokemonRepository) {
    suspend operator fun  invoke(offset: Int): ArrayList<Pokemon> {
        return repository.getPokemons(offset)
    }
}