package com.weatherapp.data.repository

import com.weatherapp.BuildConfig
import com.weatherapp.data.api.WeatherApiService
import com.weatherapp.data.local.dao.WeatherDao
import com.weatherapp.data.local.entity.WeatherCacheEntity
import com.weatherapp.data.model.ForecastResponse
import com.weatherapp.data.model.WeatherResponse
import com.weatherapp.utils.Constants
import com.weatherapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    private val api: WeatherApiService,
    private val dao: WeatherDao
) {

    private val apiKey = BuildConfig.WEATHER_API_KEY

    fun getCurrentWeather(
        city: String
    ): Flow<Resource<WeatherResponse>> = flow {

        emit(Resource.Loading)

        val cached = dao.getWeather(city)

        val cacheExpiry =
            TimeUnit.MINUTES.toMillis(
                Constants.CACHE_EXPIRY_MINUTES
            )

        if (
            cached != null &&
            System.currentTimeMillis() - cached.cachedAt < cacheExpiry
        ) {
            emit(
                Resource.Success(
                    cached.toWeatherResponse()
                )
            )
        }

        try {

            val response =
                api.getCurrentWeatherByCity(
                    city,
                    apiKey
                )

            dao.insertWeather(
                response.toCacheEntity()
            )

            emit(Resource.Success(response))

        } catch (e: Exception) {

            if (cached != null) {

                emit(
                    Resource.Error(
                        "Không thể kết nối, hiển thị dữ liệu cũ"
                    )
                )

            } else {

                emit(
                    Resource.Error(
                        e.localizedMessage
                            ?: "Lỗi không xác định"
                    )
                )
            }
        }
    }

    fun getCurrentWeatherByCoord(
        lat: Double,
        lon: Double
    ): Flow<Resource<WeatherResponse>> = flow {

        emit(Resource.Loading)

        try {

            val response =
                api.getCurrentWeatherByCoord(
                    lat,
                    lon,
                    apiKey
                )

            dao.insertWeather(
                response.toCacheEntity()
            )

            emit(Resource.Success(response))

        } catch (e: Exception) {

            emit(
                Resource.Error(
                    e.localizedMessage
                        ?: "Lỗi vị trí"
                )
            )
        }
    }

    fun getForecast(
        city: String
    ): Flow<Resource<ForecastResponse>> = flow {

        emit(Resource.Loading)

        try {

            emit(
                Resource.Success(
                    api.getForecastByCity(
                        city,
                        apiKey
                    )
                )
            )

        } catch (e: Exception) {

            emit(
                Resource.Error(
                    e.localizedMessage
                        ?: "Lỗi dự báo"
                )
            )
        }
    }

    fun getForecastByCoord(
        lat: Double,
        lon: Double
    ): Flow<Resource<ForecastResponse>> = flow {

        emit(Resource.Loading)

        try {

            emit(
                Resource.Success(
                    api.getForecastByCoord(
                        lat,
                        lon,
                        apiKey
                    )
                )
            )

        } catch (e: Exception) {

            emit(
                Resource.Error(
                    e.localizedMessage
                        ?: "Lỗi dự báo"
                )
            )
        }
    }

    private fun WeatherResponse.toCacheEntity() =
        WeatherCacheEntity(
            cityName = name,
            tempCurrent = main.temp,
            tempMin = main.tempMin,
            tempMax = main.tempMax,
            feelsLike = main.feelsLike,
            description = weather.firstOrNull()?.description ?: "",
            iconCode = weather.firstOrNull()?.icon ?: "01d",
            humidity = main.humidity,
            windSpeed = wind.speed,
            windDeg = wind.deg,
            pressure = main.pressure,
            visibility = visibility,
            sunrise = sys.sunrise,
            sunset = sys.sunset,
            country = sys.country
        )

    private fun WeatherCacheEntity.toWeatherResponse() =
        WeatherResponse(
            id = 0L,
            name = cityName,
            dt = cachedAt / 1000,

            main = com.weatherapp.data.model.Main(
                temp = tempCurrent,
                feelsLike = feelsLike,
                tempMin = tempMin,
                tempMax = tempMax,
                pressure = pressure,
                humidity = humidity
            ),

            weather = listOf(
                com.weatherapp.data.model.WeatherDesc(
                    0,
                    "",
                    description,
                    iconCode
                )
            ),

            wind = com.weatherapp.data.model.Wind(
                windSpeed,
                windDeg
            ),

            clouds = com.weatherapp.data.model.Clouds(0),

            sys = com.weatherapp.data.model.Sys(
                country,
                sunrise,
                sunset
            ),

            visibility = visibility,

            coord = com.weatherapp.data.model.Coord(
                0.0,
                0.0
            )
        )
}
