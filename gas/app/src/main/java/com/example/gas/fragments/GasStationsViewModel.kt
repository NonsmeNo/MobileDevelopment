package com.example.gas.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.gas.data.GasStations
import com.example.gas.repository.GasStationsRepository

class GasStationsViewModel : ViewModel() {
    var gasStationsList: LiveData<List<GasStations>> = GasStationsRepository.getInstance().gasStationsList

    private var _gasStations: GasStations? = null

    val gasStations get() = _gasStations

    init{
        GasStationsRepository.getInstance().gasStation.observeForever{
            _gasStations = it
        }
    }

    fun deleteGasStation(){
        if(gasStations != null){
            GasStationsRepository.getInstance().deleteGasStation(gasStations!!)
        }
    }

    fun appendGasStation(name: String){
        val gasStations = GasStations()
        gasStations.name = name
        GasStationsRepository.getInstance().newGasStation(gasStations)
    }

    fun updateGasStation(name: String){
        if (_gasStations != null){
            _gasStations!!.name = name
            GasStationsRepository.getInstance().updateGasStation(_gasStations!!)
        }
    }

    fun setCurrentGasStation(position: Int){
        if((gasStationsList.value?.size ?: 0)>position)
            gasStationsList.value?.let{GasStationsRepository.getInstance().setCurrentGasStation(it.get(position))}
    }

    fun setCurrentGasStation(gasStations: GasStations){
        GasStationsRepository.getInstance().setCurrentGasStation(gasStations)
    }

    val getGasStationsListPosition
        get() = gasStationsList.value?.indexOfFirst{it.id==gasStations?.id} ?: -1

}