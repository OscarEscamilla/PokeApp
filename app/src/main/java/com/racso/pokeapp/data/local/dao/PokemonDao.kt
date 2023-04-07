package com.racso.pokeapp.data.local.dao

import androidx.room.*
import com.racso.pokeapp.data.local.entity.PokemonEntity

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemon_table")
    suspend fun getAllPokemons(): List<PokemonEntity>

    @Query("SELECT * FROM pokemon_table WHERE favorite = 1")
    suspend fun getFavoritesPokemon(): List<PokemonEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllPokemons(pokemonList : List<PokemonEntity>)

    @Query("DELETE FROM pokemon_table WHERE favorite = 0")
    suspend fun deletePokemons()

    @Update
    suspend fun updateToFavoritePokemon(pokemonEntity: PokemonEntity)

}