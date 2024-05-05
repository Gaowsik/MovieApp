package com.example.movieapp.data.repository

import com.example.movieapp.domain.Movie
import com.example.movieapp.data.sources.local.LocalDataSource
import com.example.movieapp.data.sources.remote.RemoteDataSource
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : MovieRepository {
    override fun getMovies(): List<Movie> = localDataSource.getMovies()


    override fun getMovieById(movieId: Int): Movie =
        localDataSource.getMovieById(movieId)

    override suspend fun refreshMovies() {
        val moviesResponse = remoteDataSource.getMovies()
        val movies = moviesResponse.results
            .map {
                it.toMovie()
            }
        localDataSource.setPlanets(movies)
    }

    override suspend fun addFavouriteById(movieId: Int) =
        localDataSource.addFavouriteById(movieId)
}