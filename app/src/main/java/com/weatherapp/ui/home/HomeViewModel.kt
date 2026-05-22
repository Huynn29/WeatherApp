package com.weatherapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapp.data.model.ForecastResponse
import com.weatherapp.data.model.WeatherResponse
import com.weatherapp.data.repository.WeatherRepository
import com.weatherapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _weather =
        MutableLiveData<Resource<WeatherResponse>>()

    val weather: LiveData<Resource<WeatherResponse>>
        = _weather

    private val _forecast =
        MutableLiveData<Resource<ForecastResponse>>()

    val forecast: LiveData<Resource<ForecastResponse>>
        = _forecast

    private val _city =
        MutableLiveData<String>()

    val city: LiveData<String>
        = _city

    fun loadWeatherByCity(
        cityName: String
    ) {

        _city.value = cityName

        viewModelScope.launch {

            repository.getCurrentWeather(cityName)
                .onEach {
                    _weather.value = it
                }
                .launchIn(this)

            repository.getForecast(cityName)
                .onEach {
                    _forecast.value = it
                }
                .launchIn(this)
        }
    }

    fun loadWeatherByLocation(
        lat: Double,
        lon: Double
    ) {

        viewModelScope.launch {

            repository.getCurrentWeatherByCoord(lat, lon)
                .onEach {
                    _weather.value = it
                }
                .launchIn(this)

            repository.getForecastByCoord(lat, lon)
                .onEach {
                    _forecast.value = it
                }
                .launchIn(this)
        }
    }

    fun refresh() {
        _city.value?.let {
            loadWeatherByCity(it)
        }
    }
}
