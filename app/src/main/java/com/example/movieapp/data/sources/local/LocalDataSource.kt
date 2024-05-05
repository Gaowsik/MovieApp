package com.example.movieapp.data.sources.local

import com.example.movieapp.domain.model.Movie

interface LocalDataSource {
    suspend fun getMovies(): List<Movie>
    suspend fun getMovieById(planetId: Int): Movie
    suspend fun setPlanets(planets: List<Movie>)
    suspend fun addFavouriteById(id: Int) : Int
}