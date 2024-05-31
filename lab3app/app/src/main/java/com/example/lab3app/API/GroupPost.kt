package com.example.lab3app.API

import com.example.lab3app.data.Faculty
import com.example.lab3app.data.Group
import com.google.gson.annotations.SerializedName

class GroupPost (
    @SerializedName("action") val action: Int,
    @SerializedName("group") val group : Group
)