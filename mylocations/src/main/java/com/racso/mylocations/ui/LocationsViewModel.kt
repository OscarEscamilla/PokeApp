package com.racso.mylocations.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.racso.mylocations.core.Resource
import com.racso.mylocations.data.LocationsRepository
import com.racso.mylocations.data.model.Location
import kotlinx.coroutines.Dispatchers

class LocationsViewModel(
    private  val locationsRepository: LocationsRepository
): ViewModel() {

    val locations = liveData<Resource<List<Location>>>(Dispatchers.IO){
        emit(Resource.Loading())
        try {
            emit(Resource.Succes(locationsRepository.getAllLocations()))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }
}

class LocationsViewModelFactory(private val repo: LocationsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(LocationsRepository::class.java).newInstance(repo)
    }
}