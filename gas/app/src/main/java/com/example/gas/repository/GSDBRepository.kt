package com.example.gas.repository

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gas.data.Cars
import com.example.gas.data.GasStations
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface GSDBRepository {
    //Заправки
    fun getGasStations(): Flow<List<GasStations>>
    suspend fun insertGasStation(gasStation: GasStations)
    suspend fun insertListGasStations(gasStationsList: List<GasStations>)
    suspend fun deleteGasStation(gasStation: GasStations)
    suspend fun deleteAllGasStations()


    //Машины
    fun getAllCars(): Flow<List<Cars>>
    fun getGasStationCars(gasStationID: UUID): Flow<List<Cars>>
    suspend fun insertCar(car: Cars)
    suspend fun deleteCar(car: Cars)
    suspend fun deleteAllCars()
}