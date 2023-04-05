package com.racso.pokeapp.ui.pokemons

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.racso.pokeapp.core.Resource
import com.racso.pokeapp.data.model.Pokemon
import com.racso.pokeapp.domain.GetPokemonsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonsViewModel @Inject constructor(
    private val getPokemonsUseCase: GetPokemonsUseCase
) : ViewModel() {

    val pokemonsList: MutableLiveData<Resource<ArrayList<Pokemon>>> by lazy {
        MutableLiveData<Resource<ArrayList<Pokemon>>>()
    }

    fun getPokemons(offset: Int){
        viewModelScope.launch(Dispatchers.IO){
            try {
                pokemonsList.postValue(Resource.Loading())
                pokemonsList.postValue(Resource.Succes(getPokemonsUseCase(offset)))
            }catch (e: Exception){
                e.printStackTrace()
                pokemonsList.postValue(Resource.Failure(e))
            }
        }
    }



}