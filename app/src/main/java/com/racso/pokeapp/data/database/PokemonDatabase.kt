package com.racso.pokeapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.racso.pokeapp.data.database.dao.PokemonDao
import com.racso.pokeapp.data.database.entity.PokemonEntity


@Database(entities = [PokemonEntity::class], version = 1)
abstract class PokemonDatabase: RoomDatabase() {
    abstract fun getPokemonDao(): PokemonDao
}