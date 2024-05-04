package com.example.movieapp.data.sources.remote

import com.example.movieapp.data.models.Movie

interface RemoteDataSource {
    suspend fun getMovies(): List<Movie>
}