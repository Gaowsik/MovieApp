package com.example.movieapp.data.repository

import com.example.movieapp.data.utills.WorkResult
import com.example.movieapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
     fun getMovies(): Flow<WorkResult<List<Movie>>>
    suspend fun getMovieById(movieId: Int): Flow<WorkResult<Movie>>
    suspend fun refreshMovies(term : String, country : String, media : String)
    suspend fun addFavouriteById(movieId: Int) : Int
}