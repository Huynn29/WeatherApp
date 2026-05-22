package com.weatherapp.utils

sealed class Resource<out T> {

    object Loading : Resource<Nothing>()

    data class Success<T>(
        val data: T
    ) : Resource<T>()

    data class Error(
        val message: String,
        val code: Int? = null
    ) : Resource<Nothing>()

    val isLoading
        get() = this is Loading

    val isSuccess
        get() = this is Success

    val isError
        get() = this is Error
}
