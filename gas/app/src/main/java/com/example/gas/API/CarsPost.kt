package com.example.gas.API

import com.example.gas.data.Cars
import com.google.gson.annotations.SerializedName

class CarsPost (
    @SerializedName("action") val action: Int,
    @SerializedName("cars") val cars : Cars
)