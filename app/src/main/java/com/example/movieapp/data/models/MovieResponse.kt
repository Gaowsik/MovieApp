package com.example.movieapp.data.models

import com.google.gson.annotations.SerializedName


data class MovieResponse(
    @field:SerializedName("resultCount") val resultCount: Int,
    @field:SerializedName("results") val results: List<Movie>
)