package com.example.movieapp.data.repository

import com.example.movieapp.domain.model.Movie
import com.example.movieapp.data.sources.local.LocalDataSource
import com.example.movieapp.data.sources.remote.RemoteDataSource
import com.example.movieapp.data.utills.WorkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : MovieRepository {
    override  fun getMovies(): Flow<WorkResult<List<Movie>>> = localDataSource.getMovies()


    override suspend fun getMovieById(movieId: Int): Flow<WorkResult<Movie>> =
        localDataSource.getMovieById(movieId)

    override suspend fun refreshMovies(term : String, country : String, media : String) {
        val moviesResponse = remoteDataSource.getMovies(term,country,media)
        val movies = moviesResponse.results
            .map {
                it.toMovie()
            }
        localDataSource.setMovies(movies)
    }

    override suspend fun addFavouriteById(movieId: Int) =
        localDataSource.addFavouriteById(movieId)
}