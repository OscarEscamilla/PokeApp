package com.racso.pokeapp.ui.pokemons

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.racso.pokeapp.core.Resource
import com.racso.pokeapp.data.model.Pokemon
import com.racso.pokeapp.domain.GetPokemonByNameUseCase
import com.racso.pokeapp.domain.GetPokemonsListUseCase
import com.racso.pokeapp.domain.SaveFavoritePokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonsViewModel @Inject constructor(
    private val getPokemonsListUseCase: GetPokemonsListUseCase,
    private val getPokemonUseCase: GetPokemonByNameUseCase,
    private val saveFavoritePokemon: SaveFavoritePokemon
) : ViewModel() {

    val pokemonsList: MutableLiveData<Resource<List<Pokemon>>> by lazy {
        MutableLiveData<Resource<List<Pokemon>>>()
    }

    private val _pokemon: MutableLiveData<Resource<Pokemon>> by lazy { MutableLiveData<Resource<Pokemon>>() }
    val pokemon: LiveData<Resource<Pokemon>> = _pokemon

    fun getPokemonsList(offset: Int){
        viewModelScope.launch(Dispatchers.IO){
            try {
                pokemonsList.postValue(Resource.Loading())
                pokemonsList.postValue(Resource.Succes(getPokemonsListUseCase(offset)))
            }catch (e: Exception){
                e.printStackTrace()
                pokemonsList.postValue(Resource.Failure(e))
            }
        }
    }

    fun getPokemonById(name: String){
        viewModelScope.launch(Dispatchers.IO){
            try {
                _pokemon.postValue(Resource.Loading())
                _pokemon.postValue(Resource.Succes(getPokemonUseCase(name)))
            }catch (e: Exception){
                e.printStackTrace()
                _pokemon.postValue(Resource.Failure(e))
            }
        }
    }


    fun saveFavorite(pokemon: Pokemon){
        viewModelScope.launch() {
            saveFavoritePokemon(pokemon)
        }
    }



}