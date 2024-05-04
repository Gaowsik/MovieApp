package com.example.movieapp.data.utills.state_models

data class Resource<out T> constructor(
    val state: ResourceState,
    val data: T? = null,
    var message: String? = null,
    var responseCode: Int? = null,
)
