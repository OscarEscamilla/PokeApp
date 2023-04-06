package com.racso.pokeapp.data.network

import com.racso.pokeapp.data.network.responses.PokemonList
import com.racso.pokeapp.data.network.responses.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApiService {

    @GET("pokemon")
    suspend fun getPokemons(@Query("limit") limit: Int, @Query("offset") offset: Int) : PokemonList

    @GET("pokemon/{name}")
    suspend fun getPokemon(@Path("name") name: String): PokemonResponse


}