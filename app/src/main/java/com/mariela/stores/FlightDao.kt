package com.mariela.stores

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface FlightDao {
    @Query("SELECT * FROM FlightEntity")
    fun getAllFlights(): MutableList<FlightEntity>

    @Insert
    fun addFlight(flightEntity: FlightEntity): Long

    @Update
    fun updateFlight(flightEntity: FlightEntity)

    @Delete
    fun deleteFlight(flightEntity: FlightEntity)
}