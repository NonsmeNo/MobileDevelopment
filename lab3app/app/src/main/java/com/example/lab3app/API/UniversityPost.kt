package com.example.lab3app.API

import com.example.lab3app.data.University
import com.google.gson.annotations.SerializedName

class UniversityPost (
    @SerializedName("action") val action: Int,
    @SerializedName("university") val university : University
)