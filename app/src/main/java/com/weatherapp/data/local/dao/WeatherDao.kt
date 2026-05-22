package com.weatherapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.weatherapp.data.local.entity.WeatherCacheEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather_cache WHERE cityName = :city")
    fun observeWeather(
        city: String
    ): Flow<WeatherCacheEntity?>

    @Query("SELECT * FROM weather_cache WHERE cityName = :city LIMIT 1")
    suspend fun getWeather(
        city: String
    ): WeatherCacheEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(
        weather: WeatherCacheEntity
    )

    @Query("DELETE FROM weather_cache WHERE cachedAt < :expiry")
    suspend fun deleteExpiredCache(
        expiry: Long
    )

    @Query("SELECT * FROM weather_cache ORDER BY cachedAt DESC")
    fun getAllCachedCities(): Flow<List<WeatherCacheEntity>>
}
