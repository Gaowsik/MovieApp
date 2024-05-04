package com.example.movieapp.data.utills.state_models

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<Resource<T>>.setSuccess(data: T?, message: String? = null) =
    postValue(Resource(ResourceState.SUCCESS, data, message))

fun <T> MutableLiveData<Resource<T>>.setLoading() = postValue(
    Resource(
        ResourceState.LOADING, value?.data
    )
)

fun <T> MutableLiveData<Resource<T>>.setError(response: Resource<String>?) = postValue(
    Resource(
        ResourceState.ERROR,
        value?.data,
        message = response?.message,
        responseCode = response?.responseCode
    )
)

/**
 * use this extension method to pass through validation messages to the UI
 */
fun <T> MutableLiveData<Resource<T>>.setErrorString(message: String?) = postValue(
    Resource(
        ResourceState.ERROR, value?.data, message = message
    )
)