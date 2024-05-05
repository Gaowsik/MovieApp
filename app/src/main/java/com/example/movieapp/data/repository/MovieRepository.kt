package com.example.movieapp.data.repository

import com.example.movieapp.domain.model.Movie

interface MovieRepository {
    suspend fun getMovies(): List<Movie>
    suspend fun getMovieById(movieId: Int): Movie
    suspend fun refreshMovies(term : String, country : String, media : String)
    suspend fun addFavouriteById(movieId: Int) : Int
}