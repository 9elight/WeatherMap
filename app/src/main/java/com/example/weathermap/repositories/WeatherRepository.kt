package com.example.weathermap.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weathermap.db.WeatherDao
import com.example.weathermap.db.WeatherDataBase
import com.example.weathermap.db.WeatherDb
import com.example.weathermap.model.weatherModel.WeatherMainModel
import com.example.weathermap.network.ApiService
import com.example.weathermap.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
    private const val WEATHER_KEY = "c6e381d8c7ff98f0fee43775817cf6ad"
    private const val BASE_URL = "http://api.openweathermap.org/"

class WeatherRepository(private val weatherDataBase: WeatherDataBase,private val weatherDao: WeatherDao) {
    private lateinit var api: ApiService
    suspend fun getWeatherData(units: String, lat: String, lon: String): MutableLiveData<WeatherMainModel> {
        api = RetrofitClient.instateRetrofit(BASE_URL)!!
        val data = MutableLiveData<WeatherMainModel>()
        data.postValue(api.getWeatherData(units,lat,lon, WEATHER_KEY).await())
        return data

    }

    fun saveWeatherInDb(weather:WeatherDb){
        weatherDao.insertWeather(weather)
    }

    fun getWeather():LiveData<List<WeatherDb>>{
        return weatherDao.getAllWeather()
    }
    fun deleteWeather(id:String){
        return weatherDao.deleteWeatherById(id)
    }

}

