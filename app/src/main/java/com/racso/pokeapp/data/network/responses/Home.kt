package com.racso.pokeapp.data.network.responses

import com.google.gson.annotations.SerializedName

data class Home(
    @SerializedName("front_default")
    val frontDefault: String,
    @SerializedName("front_shiny")
    val frontShiny: String,
)