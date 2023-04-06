package com.racso.pokeapp.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.racso.pokeapp.core.Resource
import com.racso.pokeapp.data.model.Pokemon
import com.racso.pokeapp.domain.GetFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase) :
    ViewModel() {


    val fetchFavorites = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Succes(getFavoritesUseCase()))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

}