package com.racso.pokeapp.ui.favorites

import androidx.lifecycle.*
import com.racso.pokeapp.core.Resource
import com.racso.pokeapp.data.model.Pokemon
import com.racso.pokeapp.domain.GetFavoritesUseCase
import com.racso.pokeapp.domain.SaveFavoritePokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase):
    ViewModel() {


    init {
        getPokemonsList()
    }

    val favoritesList: MutableLiveData<Resource<List<Pokemon>>> by lazy {
        MutableLiveData<Resource<List<Pokemon>>>()
    }

    fun getPokemonsList(){
        viewModelScope.launch(Dispatchers.IO){
            try {
                favoritesList.postValue(Resource.Loading())
                favoritesList.postValue(Resource.Succes(getFavoritesUseCase()))
            }catch (e: Exception){
                e.printStackTrace()
                favoritesList.postValue(Resource.Failure(e))
            }
        }
    }


}