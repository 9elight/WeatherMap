package com.example.weathermap.ui.city

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weathermap.model.countriesModel.Countries
import com.example.weathermap.repositories.CountryRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CityViewModel(private  val cRepository:  CountryRepository) : ViewModel(){
    var allCountries: MutableLiveData<List<Countries>> = MutableLiveData<List<Countries>>()
    var filteredList: MutableLiveData<List<Countries>> = MutableLiveData<List<Countries>>()
    fun getCityInfo(city: String) : Observable<List<Countries>>{
      return cRepository.getCitiesInfo(city)
    }

    fun getAllCountries(){
        allCountries = cRepository.getAllCountries()
    }

    fun filterTheList(textToSearch:String,list:List<Countries>){
        val changedList = list.groupBy {
            it.name.contains(textToSearch)
        }
        if (changedList[true] != null) {
            filteredList.value = changedList.getValue(true)
        } else {

        }
    }

}