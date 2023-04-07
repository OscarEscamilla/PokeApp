package com.racso.pokeapp.data.model


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
    var favorite: Int = 0,
)


