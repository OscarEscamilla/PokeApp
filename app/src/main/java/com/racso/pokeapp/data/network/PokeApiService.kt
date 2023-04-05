package com.racso.pokeapp.data.network

import com.racso.pokeapp.data.network.model.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PokeApiService {

    @GET("pokemon")
    suspend fun getPokemons(@Query("limit") limit: Int, @Query("offset") offset: Int) : PokemonResponse


}