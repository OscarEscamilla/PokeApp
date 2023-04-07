package com.racso.pokeapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.racso.pokeapp.data.local.dao.PokemonDao
import com.racso.pokeapp.data.local.entity.PokemonEntity


@Database(entities = [PokemonEntity::class], version = 1)
abstract class PokemonDatabase: RoomDatabase() {
    abstract fun getPokemonDao(): PokemonDao
}