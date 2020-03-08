package com.example.weathermap.di

import com.example.weathermap.network.RetrofitClient
import com.example.weathermap.repositories.CountryRepository
import com.example.weathermap.repositories.WeatherRepository
import com.example.weathermap.ui.city.CityViewModel
import com.example.weathermap.ui.map.MapViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        RetrofitClient()
    }

    single {
        WeatherRepository()
    }
    single {
        CountryRepository()
    }

    viewModel {
        MapViewModel(get())
    }
    viewModel {
        CityViewModel(get())
    }

}