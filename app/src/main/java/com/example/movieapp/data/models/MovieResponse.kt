package com.example.movieapp.data.models

import com.example.movieapp.domain.Movie
import com.google.gson.annotations.SerializedName


data class MovieResponse(
    @field:SerializedName("resultCount") val resultCount: Int,
    @field:SerializedName("results") val results: List<MovieResponseData>
)