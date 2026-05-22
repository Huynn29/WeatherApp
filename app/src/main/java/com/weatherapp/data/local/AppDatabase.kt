package com.weatherapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.weatherapp.data.local.dao.WeatherDao
import com.weatherapp.data.local.entity.WeatherCacheEntity

@Database(
    entities = [WeatherCacheEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao
}
