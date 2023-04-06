package com.racso.pokeapp.data.network.responses

import com.racso.pokeapp.data.model.Pokemon


data class PokemonList(val results: ArrayList<PokemonEntry> = arrayListOf())


fun PokemonList.toPokemonList(): List<Pokemon> {
    return this.results.map { it.toPokemon() }
}
