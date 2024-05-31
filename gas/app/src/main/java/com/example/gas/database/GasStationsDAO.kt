package com.example.gas.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gas.data.Cars
import com.example.gas.data.GasStations
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface GasStationsDAO {

    //Заправки
    @Query("select * from GasStations order by gas_station_name")
    fun getGasStations(): Flow<List<GasStations>>

    @Insert(entity = GasStations::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGasStation(gasStation: GasStations)

    @Insert(entity = GasStations::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListGasStations(gasStationsList: List<GasStations>)

    @Delete(entity = GasStations::class)
    suspend fun deleteGasStation(gasStation: GasStations)

    @Query("delete from GasStations")
    suspend fun deleteAllGasStations()



    //Машины
    @Query("select * from Cars order by number_car")
    fun getAllCars(): Flow<List<Cars>>

    @Query("select * from Cars where gas_station_id=:gasStationID")
    fun getGasStationCars(gasStationID: UUID): Flow<List<Cars>>

    @Insert(entity = Cars::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCar(car: Cars)

    @Delete(entity = Cars::class)
    suspend fun deleteCar(car: Cars)

    @Query("delete from Cars")
    suspend fun deleteAllCars()
}