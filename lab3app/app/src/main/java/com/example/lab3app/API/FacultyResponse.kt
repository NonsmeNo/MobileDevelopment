package com.example.lab3app.API

import com.example.lab3app.data.Faculty
import com.example.lab3app.data.University
import com.google.gson.annotations.SerializedName

class FacultyResponse {
    @SerializedName ("items") lateinit var items: List<Faculty>
}