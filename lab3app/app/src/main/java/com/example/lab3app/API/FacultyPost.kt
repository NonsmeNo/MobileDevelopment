package com.example.lab3app.API

import com.example.lab3app.data.Faculty
import com.example.lab3app.data.University
import com.google.gson.annotations.SerializedName

class FacultyPost (
    @SerializedName("action") val action: Int,
    @SerializedName("faculty") val faculty : Faculty
)