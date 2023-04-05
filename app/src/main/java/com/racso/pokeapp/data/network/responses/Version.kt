package com.racso.pokeapp.data.network.responses


import com.google.gson.annotations.SerializedName

data class Version(
    val name: String,
    val url: String
)