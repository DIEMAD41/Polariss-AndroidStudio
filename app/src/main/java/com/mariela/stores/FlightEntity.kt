package com.mariela.stores

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FlightEntity")
data class FlightEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var origen: String,
    var destino: String,
    var fechaIda: String,
    var fechaRegreso: String,
    var nPasajeros: Int,
    var clase: String,
    var equipaje: String
)