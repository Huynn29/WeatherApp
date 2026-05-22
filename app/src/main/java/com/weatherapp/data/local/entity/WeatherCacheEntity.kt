package com.weatherapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_cache")
data class WeatherCacheEntity(

    @PrimaryKey
    val cityName: String,

    val tempCurrent: Double,
    val tempMin: Double,
    val tempMax: Double,
    val feelsLike: Double,

    val description: String,
    val iconCode: String,

    val humidity: Int,

    val windSpeed: Double,
    val windDeg: Int,

    val pressure: Int,
    val visibility: Int,

    val sunrise: Long,
    val sunset: Long,

    val country: String,

    val cachedAt: Long = System.currentTimeMillis()
)
