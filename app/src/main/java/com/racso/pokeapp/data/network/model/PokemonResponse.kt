package com.racso.pokeapp.data.network.model

import com.racso.pokeapp.data.model.Pokemon

data class PokemonResponse(val results: ArrayList<Pokemon> = arrayListOf())
