package com.example.weathermap.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.weathermap.model.weatherModel.WeatherMainModel
import com.example.weathermap.network.ApiService
import com.example.weathermap.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
    private const val WEATHER_KEY = "c6e381d8c7ff98f0fee43775817cf6ad"
    private const val BASE_URL = "http://api.openweathermap.org/"

class WeatherRepository {
    private lateinit var api: ApiService
    fun getWeatherData(units: String, lat: String, lon: String): MutableLiveData<WeatherMainModel> {
        api = RetrofitClient.instateRetrofit(BASE_URL)!!
        val data = MutableLiveData<WeatherMainModel>()
        api.getWeatherData(units, lat, lon, WEATHER_KEY)
            .enqueue(object : Callback<WeatherMainModel> {
                override fun onResponse(
                    call: Call<WeatherMainModel>,
                    response: Response<WeatherMainModel>
                ) {
                    Log.e("ololo",call.request().url.toString())
                    data.value = response.body()
                }

                override fun onFailure(call: Call<WeatherMainModel>, t: Throwable) {
                    Log.e("ololo",call.request().url.toString())
                    data.value = null

                }
            })
        return data
    }
}
