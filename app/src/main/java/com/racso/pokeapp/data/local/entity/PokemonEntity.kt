package com.racso.pokeapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.racso.pokeapp.data.model.Pokemon


@Entity(tableName = "pokemon_table")
data class PokemonEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id") val id: Int = -1,
    @ColumnInfo("name") val name: String = "",
    @ColumnInfo("image_list") val image_list: String = "",
    @ColumnInfo("image_detail") val image_detail: String = "",
    @ColumnInfo("weight") val weight: Int = 0,
    @ColumnInfo("height") val height: Int = 0,
    @ColumnInfo("hp") val hp: Int = 0,
    @ColumnInfo("attack") val attack: Int = 0,
    @ColumnInfo("defense") val defense: Int = 0,
    @ColumnInfo("special-attack") val special_attack: Int = 0,
    @ColumnInfo("favorite") val favorite: Int = 0,
    )


fun List<PokemonEntity>.toPokemonList(): List<Pokemon>{
    return this.map { it.toPokemon() }
}

fun PokemonEntity.toPokemon() = Pokemon(
    id = id,
    name = name,
    image_list = image_list,
    image_detail = image_detail,
    weight = weight,
    height = height,
    hp = hp,
    attack = attack,
    defense = defense,
    special_attack = special_attack,
    favorite = favorite
)


fun Pokemon.toEntity() = PokemonEntity(
    id = id,
    name = name,
    image_list = image_list,
    image_detail = image_detail,
    weight = weight,
    height = height,
    hp = hp,
    attack = attack,
    defense = defense,
    special_attack = special_attack,
    favorite = favorite
)





