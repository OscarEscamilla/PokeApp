package com.racso.pokeapp.data.network.responses

import com.racso.pokeapp.core.Constants
import com.racso.pokeapp.data.model.Pokemon

data class PokemonEntry(
    val name: String = "",
    val url: String = "")

fun PokemonEntry.getImageUrl(): String {
    val imgUrlArray = this.url.split("/")
    val imgName = imgUrlArray[imgUrlArray.size - 2] + ".png"
    return Constants.IMAGES_URL + imgName
}

fun PokemonEntry.getId(): Int {
    val imgUrlArray = this.url.split("/")
    val id = imgUrlArray[imgUrlArray.size - 2].toInt()
    return id
}

fun PokemonEntry.toPokemon() = Pokemon(
    id = this.getId(),
    name = name,
    image_list = this.getImageUrl()
)

