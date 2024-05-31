package com.example.gas.API

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

const val GET_GASSTATIONS = 10
const val APPEND_GASSTATIONS = 11
const val UPDATE_GASSTATIONS = 12
const val DELETE_GASSTATIONS = 13

const val GET_CARS = 20
const val APPEND_CARS = 21
const val UPDATE_CARS = 22
const val DELETE_CARS = 23

interface GasStationsAPI {
    @GET("?code=$GET_GASSTATIONS")
    fun getGasStations(): Call<GasStationsResponse>
    @Headers("Content-Type: application/json")
    @POST("gasstations")
    fun postGasStations(@Body postGasStations: GasStationsPost): Call<PostResult>

    @GET("?code=$GET_CARS")
    fun getCars(): Call<CarsResponse>
    @Headers("Content-Type: application/json")
    @POST("cars")
    fun postCars(@Body postCars: CarsPost): Call<PostResult>
}