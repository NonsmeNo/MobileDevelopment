package com.example.lab3app.API

import com.example.lab3app.data.Faculty
import com.example.lab3app.data.Student
import com.example.lab3app.data.University
import com.google.gson.annotations.SerializedName

class StudentPost (
    @SerializedName("action") val action: Int,
    @SerializedName("student") val student : Student
)