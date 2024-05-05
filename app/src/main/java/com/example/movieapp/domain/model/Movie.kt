package com.example.movieapp.domain.model


data class Movie(
    val trackId: Int,
    val trackName: String,
    val artworkUrl100: String,
    val trackPrice: Double,
    val primaryGenreName: String,
    val longDescription: String,
    val isFavourite: Boolean = false
)
