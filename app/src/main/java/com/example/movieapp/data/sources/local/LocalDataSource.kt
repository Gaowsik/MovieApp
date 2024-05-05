package com.example.movieapp.data.sources.local

import com.example.movieapp.domain.Movie

interface LocalDataSource {
    fun getMovies(): List<Movie>
    fun getMovieById(planetId: Int): Movie
    suspend fun setPlanets(planets: List<Movie>)
    suspend fun addFavouriteById(id: Int) : Int
}