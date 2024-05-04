package com.example.movieapp.data.sources.remote

import com.example.movieapp.data.models.Movie
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("search")
    suspend fun getMovies(
        @Query("term") term: String,
        @Query("country") country: String,
        @Query("media") media: String
    ) : List<Movie>
}