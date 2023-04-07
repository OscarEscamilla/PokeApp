package com.racso.pokeapp.domain

import com.racso.pokeapp.data.local.entity.toEntity
import com.racso.pokeapp.data.model.Pokemon
import com.racso.pokeapp.data.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonsListUseCase @Inject constructor(private val repository: PokemonRepository) {
    suspend operator fun  invoke(offset: Int): List<Pokemon> {
        return if (CheckNetworkUseCase().invoke()){
            val pokemonList = repository.getPokemonsFromApi(offset)
            //repository.clearPokemonTable()
            repository.savePokemons(pokemonList.map { it.toEntity() })
            pokemonList
        }else{
            repository.getAllPokemonsFromDatabase()
        }
    }
}