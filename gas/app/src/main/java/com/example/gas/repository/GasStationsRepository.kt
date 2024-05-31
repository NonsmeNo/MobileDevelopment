package com.example.gas.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.gas.API.APPEND_CARS
import com.example.gas.API.APPEND_GASSTATIONS
import com.example.gas.API.CarsPost
import com.example.gas.API.CarsResponse
import com.example.gas.API.DELETE_CARS
import com.example.gas.API.DELETE_GASSTATIONS
import com.example.gas.API.GasStationConnection
import com.example.gas.API.GasStationsAPI
import com.example.gas.API.GasStationsPost
import com.example.gas.API.GasStationsResponse
import com.example.gas.API.PostResult
import com.example.gas.API.UPDATE_CARS
import com.example.gas.API.UPDATE_GASSTATIONS
import com.example.gas.data.Cars
import com.example.gas.data.GasStations
import com.example.gas.database.GSDB
import gas.example.gas.MyApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response


const val TAG = "com.example.gas.TAG"

class GasStationsRepository private constructor() {
    companion object {
        private var INSTANCE: GasStationsRepository? = null

        fun getInstance(): GasStationsRepository {
            if (INSTANCE == null) {
                INSTANCE = GasStationsRepository()
            }
            return INSTANCE
                ?: throw IllegalStateException("GasStationsRepository: Репозиторий не инициализирован")
        }
    }

    private val gsDB by lazy {DBRepository(GSDB.getDatabase(MyApplication.context).gasStationsDAO())}
    private val myCoroutineScope = CoroutineScope(Dispatchers.Main)

    

    //заправки
    val gasStationsList: LiveData<List<GasStations>> = gsDB.getGasStations().asLiveData()
    var gasStation: MutableLiveData<GasStations> = MutableLiveData()

//    fun newGasStation(gasStations: GasStations){
//        myCoroutineScope.launch {
//            gsDB.insertGasStation(gasStations)
//            setCurrentGasStation(gasStations)
//        }
//    }
//
//    fun updateGasStation(gasStations: GasStations){
//        myCoroutineScope.launch {
//            gsDB.insertGasStation(gasStations)
//        }
//    }
//
//    fun deleteGasStation(gasStations: GasStations) {
//        myCoroutineScope.launch {
//            gsDB.deleteGasStation(gasStations)
//        }
//        setCurrentGasStation(0)
//    }

    fun setCurrentGasStation(_gasStations: GasStations) {
        gasStation.postValue(_gasStations)
    }

    fun setCurrentGasStation(position: Int) {
        if (gasStationsList.value == null || position < 0 || (gasStationsList.value?.size!! <= position))
            return
        setCurrentGasStation(gasStationsList.value!![position])
    }

    fun getGasStationsPosition(gasStations: GasStations): Int =
        gasStationsList.value?.indexOfFirst {
            it.id == gasStations.id
        } ?: 1


    //машины
    var carsList: LiveData<List<Cars>> = gsDB.getAllCars().asLiveData()
    var car: MutableLiveData<Cars> = MutableLiveData()

//    fun newCar(car: Cars) {
//        myCoroutineScope.launch {
//            gsDB.insertCar(car)
//            setCurrentCar(car)
//        }
//    }
//
//    fun updateCar(car: Cars) {
//        newCar(car)
//    }
//
//    fun deleteCar(car: Cars) {
//        myCoroutineScope.launch {
//            gsDB.deleteCar(car)
//            setCurrentCar(0)
//        }
//    }

    fun setCurrentCar(_car: Cars) {
        car.postValue(_car)
    }

    fun setCurrentCar(position: Int) {
        if (carsList.value == null || position < 0 || (carsList.value?.size!! <= position))
            return
        setCurrentCar(carsList.value!![position])
    }

    fun getCarPosition(car: Cars): Int = carsList.value?.indexOfFirst {
        it.id == car.id
    } ?: 1





    //СЕРВЕРВЕРВЕР
    //-----------------------------------------

    private var gasStationsAPI = GasStationConnection.getClient().create(GasStationsAPI::class.java)

    fun fetchGasStations(){
        gasStationsAPI.getGasStations().enqueue(object : retrofit2.Callback<GasStationsResponse>{
            override fun onFailure(call: Call<GasStationsResponse>, t: Throwable) {
                Log.d(TAG, "Ошибка получения списка заправок", t)
            }

            override fun onResponse(call: Call<GasStationsResponse>, response: Response<GasStationsResponse>) {
                if (response.code() == 200){
                    val gasStations = response.body()
                    val items = gasStations?.items ?: emptyList()
                    Log.d(TAG, "Получен список заправок $items")
                    myCoroutineScope.launch {
                        gsDB.deleteAllGasStations()
                        for(f in items){
                            gsDB.insertGasStation(f)
                        }
                    }
                }
            }

        })
    }

    private fun updateGasStation(gasStationsPost: GasStationsPost){
        gasStationsAPI.postGasStations(gasStationsPost)
            .enqueue(object: retrofit2.Callback<PostResult>{
                override fun onResponse(call: Call<PostResult>, response: Response<PostResult>) {
                    if(response.code() == 200) {
                        fetchGasStations()
                        Log.d(TAG, "Данные получены")
                    }
                }

                override fun onFailure(call: Call<PostResult>, t: Throwable) {
                    Log.d(TAG, "Ошибка изменения списка заправок", t)
                }
            })
    }

    fun newGasStation(gasStations: GasStations){
        updateGasStation(GasStationsPost(APPEND_GASSTATIONS, gasStations))
    }
    fun deleteGasStation(gasStations: GasStations){
        updateGasStation(GasStationsPost(DELETE_GASSTATIONS, gasStations))
    }
    fun updateGasStation(gasStations: GasStations){
        updateGasStation(GasStationsPost(UPDATE_GASSTATIONS, gasStations))
    }



    fun fetchCars(){
        gasStationsAPI.getCars().enqueue(object : retrofit2.Callback<CarsResponse>{
            override fun onFailure(call: Call<CarsResponse>, t: Throwable) {
                Log.d(TAG, "Ошибка получения списка машин", t)
            }

            override fun onResponse(call: Call<CarsResponse>, response: Response<CarsResponse>) {
                if (response.code() == 200){
                    val students = response.body()
                    val items = students?.items ?: emptyList()
                    Log.d(TAG, "Получен список машин $items")
                    myCoroutineScope.launch {
                        gsDB.deleteAllCars()
                        for(f in items){
                            gsDB.insertCar(f)
                        }
                    }
                }
            }

        })
    }
    private fun updateCar(carsPost: CarsPost){
        gasStationsAPI.postCars(carsPost)
            .enqueue(object: retrofit2.Callback<PostResult>{
                override fun onResponse(call: Call<PostResult>, response: Response<PostResult>) {
                    if(response.code() == 200) {
                        fetchCars()
                        Log.d(TAG, "Данные получены")
                    }
                }

                override fun onFailure(call: Call<PostResult>, t: Throwable) {
                    Log.d(TAG, "Ошибка изменения списка машин", t)
                }
            })
    }

    fun newCar(car: Cars){
        updateCar(CarsPost(APPEND_CARS, car))
    }
    fun deleteCar(car: Cars){
        updateCar(CarsPost(DELETE_CARS, car))
    }
    fun updateCar(car: Cars){
        updateCar(CarsPost(UPDATE_CARS, car))
    }



    fun loadData(){
        fetchGasStations()
        fetchCars()
    }



}