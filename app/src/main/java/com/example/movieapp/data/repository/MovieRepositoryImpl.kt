package com.example.movieapp.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.movieapp.data.models.Movie
import com.example.movieapp.data.sources.local.LocalDataSource
import com.example.movieapp.data.sources.remote.RemoteDataSource
import com.example.movieapp.data.utills.state_models.Resource
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : MovieRepository {
    override fun getMovies(): List<Movie> = localDataSource.getMovies()


    override fun getMovieById(movieId: Int): Movie =
        localDataSource.getMovieById(movieId)

    override suspend fun refreshMovies() {
        val movies = remoteDataSource.getMovies().results
        localDataSource.setPlanets(movies)
    }

    override suspend fun addFavouriteById(movieId: Int) =
        localDataSource.addFavouriteById(movieId)
}