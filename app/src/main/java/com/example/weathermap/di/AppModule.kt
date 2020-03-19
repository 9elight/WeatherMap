package com.example.weathermap.di

import android.app.Application
import androidx.room.Room
import com.example.weathermap.WeatherApp
import com.example.weathermap.db.WeatherDao
import com.example.weathermap.db.WeatherDataBase
import com.example.weathermap.network.RetrofitClient
import com.example.weathermap.repositories.CountryRepository
import com.example.weathermap.repositories.WeatherRepository
import com.example.weathermap.ui.city.CityViewModel
import com.example.weathermap.ui.map.MapViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        RetrofitClient()
    }

    single {
        WeatherRepository(get(),get())
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

    fun provideDataBase(application: Application):WeatherDataBase{
        return Room.databaseBuilder(application,WeatherDataBase::class.java,"weather_database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    fun provideDao(dataBase: WeatherDataBase): WeatherDao{
        return dataBase.weatherDao()
    }
    single { provideDao(get()) }
    single { provideDataBase(androidApplication()) }

}