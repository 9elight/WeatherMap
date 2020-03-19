package com.example.weathermap.db

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WeatherDb::class],version = 2,exportSchema = false)
abstract class WeatherDataBase (): RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}