package com.racso.pokeapp.data.repository

import com.racso.pokeapp.data.model.Pokemon
import com.racso.pokeapp.data.network.PokeApiService
import com.racso.pokeapp.data.network.model.PokemonResponse
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val api: PokeApiService
){


    suspend fun getPokemons(offset: Int): ArrayList<Pokemon> {
        return api.getPokemons(20, offset).results
    }


}