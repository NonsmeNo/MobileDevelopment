package com.example.gas.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.gas.data.Cars
import com.example.gas.data.GasStations

@Database(
    entities = [GasStations::class, Cars::class], //модели бд
    version = 4,
    exportSchema = false
)

@TypeConverters(GasStationTypeConverter::class)


abstract class GSDB: RoomDatabase() {
    abstract fun gasStationsDAO(): GasStationsDAO

    companion object{
        @Volatile
        private var INSTANCE: GSDB? = null

        fun getDatabase(context: Context): GSDB {
            return INSTANCE ?: synchronized(this){
                buildDatabase(context).also{ INSTANCE =it}
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            GSDB::class.java,
            "gas_station_database")
            .fallbackToDestructiveMigration()
            .build()
    }
}