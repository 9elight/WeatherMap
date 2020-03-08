package com.example.weathermap.ui.city

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weathermap.model.countriesModel.Countries
import com.example.weathermap.repositories.CountryRepository

class CityViewModel(private  val cRepository:  CountryRepository) : ViewModel(){
    lateinit var liveData: MutableLiveData<List<Countries>>

    fun getCityInfo(city: String) {
        liveData = cRepository.getCitiesInfo(city)
    }
}