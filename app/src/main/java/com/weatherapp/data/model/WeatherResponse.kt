package com.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val id: Long,
    val name: String,
    val dt: Long,
    val main: Main,
    val weather: List<WeatherDesc>,
    val wind: Wind,
    val clouds: Clouds,
    val sys: Sys,
    val visibility: Int,
    val coord: Coord
)

data class Main(
    val temp: Double,

    @SerializedName("feels_like")
    val feelsLike: Double,

    @SerializedName("temp_min")
    val tempMin: Double,

    @SerializedName("temp_max")
    val tempMax: Double,

    val pressure: Int,
    val humidity: Int
)

data class WeatherDesc(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Wind(
    val speed: Double,
    val deg: Int
)

data class Clouds(
    val all: Int
)

data class Sys(
    val country: String,
    val sunrise: Long,
    val sunset: Long
)

data class Coord(
    val lat: Double,
    val lon: Double
)
