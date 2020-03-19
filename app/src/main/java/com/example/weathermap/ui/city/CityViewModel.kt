package com.example.weathermap.ui.city

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathermap.model.countriesModel.Countries
import com.example.weathermap.repositories.CountryRepository
import com.example.weathermap.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CityViewModel(private  val cRepository:  CountryRepository) : ViewModel(){
    var allCountries: MutableLiveData<List<Countries>> = MutableLiveData<List<Countries>>()
    var filteredList: MutableLiveData<List<Countries>> = MutableLiveData<List<Countries>>()
    val notFoundException: SingleLiveEvent<Void> = SingleLiveEvent()
    suspend fun getCityInfo(city: String) {
        viewModelScope.launch {
            cRepository.getCitiesInfo(city)
        }
    }

     suspend fun getAllCountries(){
         viewModelScope.launch(Dispatchers.IO) {
             allCountries.postValue(cRepository.getAllCountries())
         }

    }

    fun filterTheList(textToSearch:String,list:List<Countries>){
        val changedList = list.groupBy {
            it.name.contains(textToSearch)
        }
        if (changedList[true] != null) {
            filteredList.value = changedList.getValue(true)
        } else {
            notFoundException.call()
        }
    }

}