package com.example.forecastmvvm.data.currentweather.response


import com.google.gson.annotations.SerializedName

data class Wind(
    val deg: Int,
    val speed: Double
)