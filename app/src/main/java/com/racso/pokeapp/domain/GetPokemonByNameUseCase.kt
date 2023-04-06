package com.racso.pokeapp.domain

import com.racso.pokeapp.data.model.Pokemon
import com.racso.pokeapp.data.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonByNameUseCase @Inject constructor(private val pokemonRepository: PokemonRepository) {
    suspend operator fun invoke(name: String): Pokemon {
        return pokemonRepository.getPokemon(name)
    }
}