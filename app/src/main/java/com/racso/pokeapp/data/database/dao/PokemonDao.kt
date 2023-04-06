package com.racso.pokeapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.racso.pokeapp.data.database.entity.PokemonEntity

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemon_table")
    suspend fun getAllPokemons(): List<PokemonEntity>

    @Query("SELECT * FROM pokemon_table WHERE favorite = 1")
    suspend fun getFavoritesPokemon(): List<PokemonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovies(pokemonList : List<PokemonEntity>)

    @Query("DELETE FROM pokemon_table WHERE favorite = 0")
    suspend fun deletePokemons()

}