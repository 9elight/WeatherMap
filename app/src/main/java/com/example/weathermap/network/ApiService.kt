package com.example.weathermap.network

import com.example.weathermap.model.countriesModel.Countries
import com.example.weathermap.model.weatherModel.WeatherMainModel
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

    interface ApiService {
        @GET("data/2.5/weather")
        fun getWeatherData(@Query("units") units: String,
                           @Query("lat") lat: String,
                           @Query("lon") lon: String,
                           @Query("appId") appId: String) : Deferred<WeatherMainModel>
//        https://restcountries.eu/rest/v2/capital/{capital}

        @GET("rest/v2/capital/{capital}")
        fun getCityInfo(@Path("capital") capital: String) :Deferred<List<Countries>>

        @GET("rest/v2/all")
         fun getAllCountries() : Deferred<List<Countries>>
    }
