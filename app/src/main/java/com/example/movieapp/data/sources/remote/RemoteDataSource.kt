package com.example.movieapp.data.sources.remote

import com.example.movieapp.data.models.MovieResponse

interface RemoteDataSource {
    suspend fun getMovies(): MovieResponse
}