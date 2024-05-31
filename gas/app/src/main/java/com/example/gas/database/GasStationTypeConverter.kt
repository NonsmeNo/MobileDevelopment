package com.example.gas.database

import androidx.room.TypeConverter
import java.util.Date
import java.util.UUID

class GasStationTypeConverter {

    @TypeConverter
    fun toUUID(uuid: String?): UUID?{
        return UUID.fromString(uuid)
    }

    @TypeConverter
    fun fromUUID(uuid: UUID?): String?{
        return uuid?.toString()
    }

    @TypeConverter
    fun toDate(millisSinceEpoch: Long?): Date?{
        return millisSinceEpoch?.let{
            Date(it)
        }
    }
    @TypeConverter
    fun fromDate(date: Date?): Long?{
        return date?.time
    }

}