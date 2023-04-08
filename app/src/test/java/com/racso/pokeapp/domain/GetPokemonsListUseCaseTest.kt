package com.racso.pokeapp.domain

import com.racso.pokeapp.data.local.entity.PokemonEntity
import com.racso.pokeapp.data.model.Pokemon
import com.racso.pokeapp.data.repository.PokemonRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetPokemonsListUseCaseTest {

    @RelaxedMockK
    private lateinit var pokemonRepository: PokemonRepository

    lateinit var getPokemonsListUseCase: GetPokemonsListUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getPokemonsListUseCase = GetPokemonsListUseCase(pokemonRepository)
    }

    @Test
    fun `when the api doesnt return anything then get values from database`() = runBlocking {
        //Given
        coEvery { pokemonRepository.getPokemonsFromApi(0) } returns emptyList()

        //When
        getPokemonsListUseCase(0)

        //Then
        coVerify(exactly = 1) { pokemonRepository.getAllPokemonsFromDatabase() }
    }

    @Test
    fun `when the api return pokemon list then get values from api`() = runBlocking {
        //Given
        val myList = listOf(Pokemon())
        coEvery { pokemonRepository.getPokemonsFromApi(0) } returns myList

        //When
        val response = getPokemonsListUseCase(0)

        //Then
        //coVerify(exactly = 1) { pokemonRepository.clearPokemonTable() }
        coVerify(exactly = 1) { pokemonRepository.savePokemons(listOf(PokemonEntity())) }
        coVerify(exactly = 0) { pokemonRepository.getAllPokemonsFromDatabase() }
        assert(response == myList)
    }
}