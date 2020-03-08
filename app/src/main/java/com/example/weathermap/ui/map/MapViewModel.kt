package com.example.weathermap.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weathermap.model.weatherModel.WeatherMainModel
import com.example.weathermap.repositories.WeatherRepository

class MapViewModel(private val wRepository: WeatherRepository) : ViewModel() {
    lateinit var liveData: MutableLiveData<WeatherMainModel>
    fun getWeatherData(units: String, lat: String, lon: String){
        liveData = wRepository.getWeatherData(units, lat, lon)
    }
}
