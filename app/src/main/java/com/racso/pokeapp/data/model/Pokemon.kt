package com.racso.pokeapp.data.model


import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.racso.pokeapp.data.database.entity.PokemonEntity
import com.racso.pokeapp.data.network.responses.*

data class Pokemon(
    val id: Int = -1,
    val name: String = "",
    val image_list: String = "",
    val image_detail: String = "",
    val weight: Int = 0,
    val height: Int = 0,
    val hp: Int = 0,
    val attack: Int = 0,
    val defense: Int = 0,
    val special_attack: Int = 0,
    val favorite: Int = 0,
)


