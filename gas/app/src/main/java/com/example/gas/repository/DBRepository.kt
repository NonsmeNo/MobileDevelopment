package com.example.gas.repository

import com.example.gas.data.Cars
import com.example.gas.data.GasStations
import com.example.gas.database.GasStationsDAO
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class DBRepository(val dao: GasStationsDAO): GSDBRepository {
    override fun getGasStations(): Flow<List<GasStations>> = dao.getGasStations()
    override suspend fun insertGasStation(gasStation: GasStations) = dao.insertGasStation(gasStation)
    override suspend fun insertListGasStations(gasStationsList: List<GasStations>) = dao.insertListGasStations(gasStationsList)
    override suspend fun deleteGasStation(gasStation: GasStations) = dao.deleteGasStation(gasStation)
    override suspend fun deleteAllGasStations() = dao.deleteAllGasStations()

    override fun getAllCars(): Flow<List<Cars>> = dao.getAllCars()
    override fun getGasStationCars(gasStationID: UUID): Flow<List<Cars>> = dao.getGasStationCars(gasStationID)
    override suspend fun insertCar(car: Cars) = dao.insertCar(car)
    override suspend fun deleteCar(car: Cars) = dao.deleteCar(car)
    override suspend fun deleteAllCars() = dao.deleteAllCars()
}