package com.racso.pokeapp.data.repository

import com.racso.pokeapp.data.local.dao.PokemonDao
import com.racso.pokeapp.data.local.entity.PokemonEntity
import com.racso.pokeapp.data.local.entity.toPokemonList
import com.racso.pokeapp.data.remote.PokeApiService
import com.racso.pokeapp.data.model.Pokemon
import com.racso.pokeapp.data.remote.responses.*
import javax.inject.Inject

class PokemonRepository @Inject constructor(private val api: PokeApiService, private  val pokemonDao: PokemonDao) {
    suspend fun getPokemonsFromApi(offset: Int): List<Pokemon> {
        return api.getPokemons(20, offset).toPokemonList()
    }

    suspend fun getPokemon(name: String): Pokemon {
        return api.getPokemon(name).toPokemon()
    }

    suspend fun clearPokemonTable() {
        pokemonDao.deletePokemons()
    }

    suspend fun getAllPokemonsFromDatabase(): List<Pokemon> {
        return pokemonDao.getAllPokemons().toPokemonList()
    }

    suspend fun savePokemons(pokemonList: List<PokemonEntity>){
        pokemonDao.insertAllPokemons(pokemonList)
    }

    suspend fun getFavorites(): List<PokemonEntity> {
        return pokemonDao.getFavoritesPokemon()
    }

    suspend fun saveFavorite(pokemonEntity: PokemonEntity){
        pokemonDao.updateToFavoritePokemon(pokemonEntity)
    }


}