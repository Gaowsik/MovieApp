package com.example.movieapp.data.sources.remote

import com.example.movieapp.data.models.MovieResponse
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val movieApi: MovieApi) : RemoteDataSource {

    override suspend fun getMovies(term: String, country: String, media: String): MovieResponse =
        movieApi.getMovies(term, country, media)
}