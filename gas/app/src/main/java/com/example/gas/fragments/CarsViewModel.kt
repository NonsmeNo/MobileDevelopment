package com.example.gas.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gas.data.Cars
import com.example.gas.data.GasStations
import com.example.gas.repository.GasStationsRepository
import java.util.Date

class CarsViewModel : ViewModel() {
    var carsList: MutableLiveData<List<Cars>> = MutableLiveData()

    private var _car: Cars? = null
    var isNew: Boolean? = true
    val car
        get() = _car

    var gasStation: GasStations? = null

    fun setGasStationValue(gasStation: GasStations){
        this.gasStation = gasStation
        GasStationsRepository.getInstance().carsList.observeForever{
            carsList.postValue(
                it.filter { it.gasStationID == gasStation.id } as MutableList<Cars>
            )
        }
        GasStationsRepository.getInstance().car.observeForever{
            _car = it
        }
    }



    fun sortByNumber(){
        carsList.postValue(
            carsList.value?.sortedBy {
                it.num
            }
        )
    }


    fun filterByDate(date: String) {
        carsList.postValue(
            carsList.value?.filter {
                it.date.toString() == date
            }
        )
    }

    fun sortByTypeGas(){
        carsList.postValue(
            carsList.value?.sortedBy {
                it.gas
            }
        )
    }

    fun deleteCar(){
        if(car != null){
            GasStationsRepository.getInstance().deleteCar(car!!)
        }
    }

    fun setCurrentCar(car: Cars){
        GasStationsRepository.getInstance().setCurrentCar(car)
    }
}