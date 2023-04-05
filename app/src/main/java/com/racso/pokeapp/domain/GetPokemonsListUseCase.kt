package com.racso.pokeapp.domain

import com.racso.pokeapp.data.model.PokemonEntry
import com.racso.pokeapp.data.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonsListUseCase @Inject constructor(private val repository: PokemonRepository) {
    suspend operator fun  invoke(offset: Int): ArrayList<PokemonEntry> {
        return repository.getPokemonsList(offset)
    }
}