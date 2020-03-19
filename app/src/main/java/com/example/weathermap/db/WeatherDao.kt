package com.example.weathermap.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
    @Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeather(weatherDb: WeatherDb)

    @Update
    fun updateWeather(weatherDb: WeatherDb)

    @Delete
    fun deleteWeather(weatherDb: WeatherDb)

    @Query("SELECT * FROM weather_table")
    fun getAllWeather(): LiveData<List<WeatherDb>>

    @Query("SELECT * FROM weather_table WHERE id == :id")
    fun getWeather(id:Int?):WeatherDb

    @Query("DELETE FROM weather_table WHERE id == :id")
    fun deleteWeatherById(id:String?)
}