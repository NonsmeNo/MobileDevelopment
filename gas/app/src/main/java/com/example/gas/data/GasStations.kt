package com.example.gas.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "GasStations",
    indices = [Index("id"), Index("gas_station_name")]
)

data class GasStations(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    @ColumnInfo(name="gas_station_name") var name: String = ""
)

