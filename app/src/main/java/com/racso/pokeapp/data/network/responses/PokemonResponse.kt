package com.racso.pokeapp.data.network.responses


import com.google.gson.annotations.SerializedName
import com.racso.pokeapp.data.model.Pokemon
import com.racso.pokeapp.data.network.responses.*

data class PokemonResponse(
    val abilities: List<Ability>,
    @SerializedName("base_experience")
    val baseExperience: Int,
    val forms: List<Form>,
    @SerializedName("game_indices")
    val gameIndices: List<GameIndice>,
    val height: Int,
    @SerializedName("held_items")
    val heldItems: List<Any>,
    val id: Int,
    @SerializedName("is_default")
    val isDefault: Boolean,
    @SerializedName("location_area_encounters")
    val locationAreaEncounters: String,
    val moves: List<Move>,
    val name: String,
    val order: Int,
    @SerializedName("past_types")
    val pastTypes: List<Any>,
    val species: Species,
    val sprites: Sprites,
    val stats: List<Stat>,
    val types: List<Type>,
    val weight: Int
)

fun PokemonResponse.toPokemon() = Pokemon(
    id = id,
    name = name,
    image_list = sprites.frontDefault,
    image_detail = sprites.other.home.frontDefault,
    weight = weight,
    height = height,
    hp = stats[0].baseStat,
    attack = stats[1].baseStat,
    defense = stats[2].baseStat,
    special_attack = stats[3].baseStat
)

