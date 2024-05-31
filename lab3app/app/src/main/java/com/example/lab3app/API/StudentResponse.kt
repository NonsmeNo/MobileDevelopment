package com.example.lab3app.API

import com.example.lab3app.data.Faculty
import com.example.lab3app.data.Student
import com.example.lab3app.data.University
import com.google.gson.annotations.SerializedName

class StudentResponse {
    @SerializedName ("items") lateinit var items: List<Student>
}