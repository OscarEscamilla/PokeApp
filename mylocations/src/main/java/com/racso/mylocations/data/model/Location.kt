package com.racso.mylocations.data.model

import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ServerTimestamp
import com.racso.mylocations.utils.formatToStringMap
import java.util.*

data class Location(
    @ServerTimestamp
    val created_at: Date? = null,
    val position: GeoPoint? = null
) {
    override fun toString(): String {
        return "Longitude: ${this.position?.longitude} - Latitude: ${this.position?.latitude}\nDate: ${this.created_at?.formatToStringMap()}"
    }
}

fun List<Location>.toListString(): List<String> {
    return this.map { it.toString() }
}

