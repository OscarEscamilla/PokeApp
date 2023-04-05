package com.racso.pokeapp.data.model

import com.racso.pokeapp.core.Constants

data class Pokemon(val name: String = "", val url: String = "")

fun Pokemon.getImageUrl(): String {
    val imgUrlArray = this.url.split("/")
    val imgName = imgUrlArray[imgUrlArray.size - 2] + ".png"
    return Constants.IMAGES_URL + imgName
}