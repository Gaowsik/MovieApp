package com.example.movieapp.data.sources.remote

import com.example.movieapp.data.models.Movie
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val movieApi: MovieApi): RemoteDataSource {
    override suspend fun getMovies(): List<Movie> {
        TODO("Not yet implemented")
    }


}