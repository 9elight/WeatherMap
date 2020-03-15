package com.example.weathermap.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.weathermap.model.countriesModel.Countries
import com.example.weathermap.network.ApiService
import com.example.weathermap.network.RetrofitClient
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val BASE_URL = "https://restcountries.eu/"

class CountryRepository {
    private  var api: ApiService = RetrofitClient.instateRetrofit(BASE_URL)!!
    fun getCitiesInfo(city: String) : Observable<List<Countries>>{
        return api.getCityInfo(city)
//        val data = MutableLiveData<List<Countries>>()
//        api.getCityInfo(city).enqueue(object : Callback<List<Countries>>{
//            override fun onResponse(
//                call: Call<List<Countries>>,
//                response: Response<List<Countries>>
//            ) {
//                if (response.body() != null && response.isSuccessful)
//                data.value = response.body()
//            }
//
//            override fun onFailure(call: Call<List<Countries>>, t: Throwable) {
//                data.value = null
//            }
//
//        })
    }

    fun getAllCountries() : MutableLiveData<List<Countries>>{
        val data = MutableLiveData<List<Countries>>()
        api.getAllCountries().enqueue(object : Callback<List<Countries>>{
            override fun onResponse(
                call: Call<List<Countries>>,
                response: Response<List<Countries>>
            ) {
                data.value = response.body()
            }

            override fun onFailure(call: Call<List<Countries>>, t: Throwable) {
                data.value = null
            }

        })
        return data
    }

}