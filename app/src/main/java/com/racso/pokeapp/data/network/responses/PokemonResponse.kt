package com.racso.pokeapp.data.network.responses

import com.racso.pokeapp.data.model.PokemonEntry

data class PokemonResponse(val results: ArrayList<PokemonEntry> = arrayListOf())
