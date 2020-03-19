package com.example.weathermap.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "weather_table")
data class WeatherDb(
    @PrimaryKey()
    val id: String,
    val lat:String,
    val lng:String,
    val temp: String,
    val humidity:String,
    val weatherDesc:String,
    val city:String,
    val weatherIc:String
)