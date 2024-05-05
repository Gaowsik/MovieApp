package com.example.movieapp.data.repository

import com.example.movieapp.domain.Movie

interface MovieRepository {
    fun getMovies(): List<Movie>
    fun getMovieById(movieId: Int): Movie
    suspend fun refreshMovies()
    suspend fun addFavouriteById(movieId: Int) : Int
}