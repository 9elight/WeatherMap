package com.example.weathermap

import android.app.Application
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.example.weathermap.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class WeatherApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WeatherApp)
            modules(appModule)
        }
    }
}