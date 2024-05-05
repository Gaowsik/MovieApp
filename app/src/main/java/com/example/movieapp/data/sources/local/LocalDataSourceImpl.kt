package com.example.movieapp.data.sources.local

import com.example.movieapp.domain.model.Movie
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val moviesDao: MoviesDao) : LocalDataSource {
    override suspend fun getMovies(): List<Movie> {
        return moviesDao.getMovies().map {
            it.toMovie()
        }
    }

    override suspend fun getMovieById(trackId: Int): Movie {
        return moviesDao.observePlanetById(trackId).toMovie()
    }

    override suspend fun setPlanets(planets: List<Movie>) {
        moviesDao.setMovies(planets.map {
            it.toMovieEntity()
        })
    }

    override suspend fun addFavouriteById(id: Int): Int =
       moviesDao.setMovieAsFavorite(id)
}