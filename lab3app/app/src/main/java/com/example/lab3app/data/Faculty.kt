package com.example.lab3app.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.UUID

@Entity(tableName = "faculties",
    indices = [Index("id"), Index("university_id")],
//    foreignKeys = [
//        ForeignKey(
//            entity = University::class,
//            parentColumns = ["id"],
//            childColumns = ["university_id"],
//            onDelete = ForeignKey.CASCADE
//        )
//    ]
)
data class Faculty(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    @SerializedName("faculty_name") @ColumnInfo(name = "faculty_name") var name: String = "",
    @SerializedName("university_id") @ColumnInfo(name = "university_id") var universityID: UUID?= null
)
