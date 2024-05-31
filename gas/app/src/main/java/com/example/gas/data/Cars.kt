package com.example.gas.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity(tableName = "Cars",
    indices = [Index("id"), Index("gas_station_id")],
    foreignKeys = [
        ForeignKey(
            entity = GasStations::class,
            parentColumns = ["id"],
            childColumns = ["gas_station_id"],
            onDelete = ForeignKey.CASCADE
        )
    ])
data class Cars(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "model_car") var model: String = "",
    @ColumnInfo(name = "number_car") var num: String = "",
    @ColumnInfo(name = "brand_car") var brand: String = "",
    @ColumnInfo(name = "color_car") var color: String = "",
    @ColumnInfo(name = "fio_owner") var owner: String = "",
    @ColumnInfo(name = "type_gas") var gas: Int=0,
    @ColumnInfo(name = "volume_gas") var volume: String = "",
    @ColumnInfo(name = "date_gas") var date: Date = Date(),
    @ColumnInfo(name = "gas_station_id") var gasStationID: UUID?= null
)