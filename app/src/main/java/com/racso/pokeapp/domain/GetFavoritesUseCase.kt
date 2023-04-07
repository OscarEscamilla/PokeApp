package com.racso.pokeapp.domain

import com.racso.pokeapp.data.local.entity.toPokemonList
import com.racso.pokeapp.data.model.Pokemon
import com.racso.pokeapp.data.repository.PokemonRepository
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(private val repository: PokemonRepository){
    suspend operator fun invoke(): List<Pokemon> {
        return repository.getFavorites().toPokemonList()
    }
}