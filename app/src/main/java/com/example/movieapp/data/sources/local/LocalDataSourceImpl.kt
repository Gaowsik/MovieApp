package com.example.movieapp.data.sources.local

import com.example.movieapp.data.utills.WorkResult
import com.example.movieapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val moviesDao: MoviesDao) : LocalDataSource {
    override fun getMovies(): Flow<WorkResult<List<Movie>>> {

        return moviesDao.getMovies().map {
            WorkResult.Success(it.map { movieEntity -> movieEntity.toMovie()  })
        }
    }

    override suspend fun getMovieById(trackId: Int): Flow<WorkResult<Movie>> {
        return moviesDao.observePlanetById(trackId).map {
           WorkResult.Success(it.toMovie())
        }
    }

    override suspend fun setMovies(movies: List<Movie>) {
        moviesDao.setMovies(movies.map {
            it.toMovieEntity()
        })
    }

    override suspend fun addFavouriteById(id: Int): Int =
       moviesDao.setMovieAsFavorite(id)
}