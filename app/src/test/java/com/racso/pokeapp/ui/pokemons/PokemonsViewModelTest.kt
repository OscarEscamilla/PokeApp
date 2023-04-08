package com.racso.pokeapp.ui.pokemons

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModel
import com.racso.pokeapp.core.Resource
import com.racso.pokeapp.data.model.Pokemon
import com.racso.pokeapp.domain.GetPokemonByNameUseCase
import com.racso.pokeapp.domain.GetPokemonsListUseCase
import com.racso.pokeapp.domain.SaveFavoritePokemon
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PokemonsViewModelTest {

    @RelaxedMockK
    private lateinit var getPokemonsListUseCase: GetPokemonsListUseCase

    @RelaxedMockK
    private lateinit var getPokemonUseCase: GetPokemonByNameUseCase

    @RelaxedMockK
    private lateinit var saveFavoritePokemon: SaveFavoritePokemon

    private lateinit var viewModel: PokemonsViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun before() {
        MockKAnnotations.init(this)
        viewModel = PokemonsViewModel(
            getPokemonsListUseCase,
            getPokemonUseCase,
            saveFavoritePokemon)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }


    @Test
    fun `when getPokemonsListUseCase return pokemon list and set value on live data`() = runTest {
        // given
        val pokemonList = listOf(Pokemon())
        coEvery { getPokemonsListUseCase(0) } returns pokemonList
        //val pokemonListLiveData = viewModel.pokemonsList.value
        // when
        viewModel.getPokemonsList(0)
        // then
        assertEquals(Resource.Succes(pokemonList), viewModel.pokemonsList.value)
    }
}


