package com.eddy.unittesting.repository.util

sealed class Resource<out RESOURCE_TYPE> {
    object Loading : Resource<Nothing>()
    data class Error<out T> (val exception: Exception, val data: T? = null) : Resource<T>()
    data class Success<out T> (val data: T) : Resource<T>()
}
