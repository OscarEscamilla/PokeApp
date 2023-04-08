package com.racso.mylocations.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.racso.mylocations.data.model.Location
import kotlinx.coroutines.tasks.await

class LocationsDataSource {

    val locationsRef = FirebaseFirestore.getInstance().collection("locations")

    suspend fun getAllLocations(): List<Location> {
        val listLocation = mutableListOf<Location>()
        val querySnapshot = locationsRef.get().await()
        for(location in querySnapshot){
            location.toObject(Location::class.java).let { listLocation.add(it) }
        }
        return listLocation.toList()
    }

    suspend fun saveLocation(location: Location){
        locationsRef.document().set(location).await()
    }

}