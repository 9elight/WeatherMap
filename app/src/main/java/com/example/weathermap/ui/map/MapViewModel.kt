package com.example.weathermap.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathermap.db.WeatherDb
import com.example.weathermap.model.weatherModel.WeatherMainModel
import com.example.weathermap.repositories.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapViewModel(private val wRepository: WeatherRepository) : ViewModel() {
    var liveData: MutableLiveData<WeatherMainModel> = MutableLiveData()
    var weatherLiveData: LiveData<List<WeatherDb>> = MutableLiveData()
    suspend fun getWeatherData(units: String, lat: String, lon: String){
        viewModelScope.launch(Dispatchers.IO) {
            liveData = wRepository.getWeatherData(units, lat, lon)
        }
    }
    fun saveWeatherInDb(weatherDb: WeatherDb){
        wRepository.saveWeatherInDb(weatherDb)
    }
    fun getWeather(){
        weatherLiveData = wRepository.getWeather()
    }
    fun deleteWeather(id:String){
        wRepository.deleteWeather(id)
    }
}
