package com.racso.pokeapp.data.network

import com.racso.pokeapp.data.network.responses.PokemonResponse
import com.racso.pokeapp.data.network.responses.Pokemon
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApiService {

    @GET("pokemon")
    suspend fun getPokemonsList(@Query("limit") limit: Int, @Query("offset") offset: Int) : PokemonResponse

    @GET("pokemon/{name}")
    suspend fun getPokemon(@Path("name") name: String): Pokemon


}