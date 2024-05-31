package com.example.gas.API

import com.example.gas.data.GasStations
import com.google.gson.annotations.SerializedName

class GasStationsResponse {
    @SerializedName("items") lateinit var items: List<GasStations>
}