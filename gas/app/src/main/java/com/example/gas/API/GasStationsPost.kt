package com.example.gas.API

import com.example.gas.data.GasStations
import com.google.gson.annotations.SerializedName

class GasStationsPost (
    @SerializedName("action") val action: Int,
    @SerializedName("gasstations") val gasstations : GasStations
)