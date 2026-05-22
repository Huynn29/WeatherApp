package com.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    val list: List<ForecastItem>,
    val city: ForecastCity
)

data class ForecastItem(
    val dt: Long,
    val main: Main,
    val weather: List<WeatherDesc>,
    val wind: Wind,

    @SerializedName("dt_txt")
    val dtTxt: String,

    val pop: Double
)

data class ForecastCity(
    val name: String,
    val country: String,
    val timezone: Int
)
