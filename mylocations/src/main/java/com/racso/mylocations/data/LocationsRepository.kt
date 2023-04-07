package com.racso.mylocations.data

import com.racso.mylocations.data.remote.LocationsDataSource
import com.upax.moviesapp.data.model.Location

class LocationsRepository(private val locationsDataSource: LocationsDataSource) {


    suspend fun getAllLocations(): List<Location> {
        return locationsDataSource.getAllLocations()
    }


    suspend fun saveLocation(location: Location) {
        locationsDataSource.saveLocation(location)
    }

}