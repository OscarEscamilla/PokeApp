package com.racso.pokeapp.domain

import com.racso.pokeapp.data.network.responses.Pokemon
import com.racso.pokeapp.data.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonUseCase @Inject constructor(private val pokemonRepository: PokemonRepository) {
    suspend operator fun invoke(name: String): Pokemon {
        return pokemonRepository.getPokemon(name)
    }
}