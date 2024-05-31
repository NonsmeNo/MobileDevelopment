package com.example.gas.API

import com.example.gas.data.Cars
import com.google.gson.annotations.SerializedName

class CarsResponse {
    @SerializedName("items") lateinit var items: List<Cars>
}

