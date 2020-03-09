package com.example.weathermap.ui.city

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weathermap.model.countriesModel.Countries
import com.example.weathermap.repositories.CountryRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CityViewModel(private  val cRepository:  CountryRepository) : ViewModel(){
    fun getCityInfo(city: String) : Observable<List<Countries>>{
      return cRepository.getCitiesInfo(city)
    }
}