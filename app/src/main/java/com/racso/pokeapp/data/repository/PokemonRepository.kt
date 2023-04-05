package com.racso.pokeapp.data.repository

import com.racso.pokeapp.data.model.PokemonEntry
import com.racso.pokeapp.data.network.PokeApiService
import com.racso.pokeapp.data.network.responses.Pokemon
import javax.inject.Inject

class PokemonRepository @Inject constructor(private val api: PokeApiService) {
    suspend fun getPokemonsList(offset: Int): ArrayList<PokemonEntry> {
        return api.getPokemonsList(20, offset).results
    }

    suspend fun getPokemon(name: String): Pokemon {
        return api.getPokemon(name)
    }


}