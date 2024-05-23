package com.example.movieapp.data.sources.local

import com.example.movieapp.data.utills.WorkResult
import com.example.movieapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun getMovies(): Flow<WorkResult<List<Movie>>>
    suspend fun getMovieById(planetId: Int): Flow<WorkResult<Movie>>
    suspend fun setMovies(movies: List<Movie>)
    suspend fun addFavouriteById(id: Int) : Int
}