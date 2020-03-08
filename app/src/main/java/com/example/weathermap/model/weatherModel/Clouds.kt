package com.example.weathermap.model.weatherModel

import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    val all: Int? = 0)