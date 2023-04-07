package com.racso.pokeapp.domain

import com.racso.pokeapp.data.local.entity.toEntity
import com.racso.pokeapp.data.model.Pokemon
import com.racso.pokeapp.data.repository.PokemonRepository
import javax.inject.Inject

class SaveFavoritePokemon @Inject constructor(private val repository: PokemonRepository) {
    suspend operator fun invoke(pokemon: Pokemon){
        repository.saveFavorite(pokemon.toEntity())
    }
}