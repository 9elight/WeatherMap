package com.example.weathermap.repositories

import com.example.weathermap.model.countriesModel.Countries
import com.example.weathermap.network.ApiService
import com.example.weathermap.network.RetrofitClient

private const val BASE_URL = "https://restcountries.eu/"

class CountryRepository {
    private var api: ApiService = RetrofitClient.instateRetrofit(BASE_URL)!!
    suspend fun getCitiesInfo(city: String): List<Countries> {
        return api.getCityInfo(city).await()
    }

    //    fun getAllCountries() : MutableLiveData<List<Countries>>{
//        val data = MutableLiveData<List<Countries>>()
//        api.getAllCountries().enqueue(object : Callback<List<Countries>>{
//            override fun onResponse(
//                call: Call<List<Countries>>,
//                response: Response<List<Countries>>
//            ) {
//                data.value = response.body()
//            }
//
//            override fun onFailure(call: Call<List<Countries>>, t: Throwable) {
//                data.value = null
//            }
//
//        })
//        return data
//    }
    suspend fun getAllCountries(): List<Countries> {
        return api.getAllCountries().await()
    }
}