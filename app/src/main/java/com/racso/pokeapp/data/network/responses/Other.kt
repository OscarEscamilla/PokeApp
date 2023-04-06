package com.racso.pokeapp.data.network.responses

import com.google.gson.annotations.SerializedName

data class Other(
    @SerializedName("dream_world")
    val dreamWorld: DreamWorld,
    @SerializedName("official-artwork")
    val officialArtwork: OfficialArtwork,
    @SerializedName("home")
    val home: Home
)