package com.example.movieapp.data.sources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Query("SELECT * FROM Movies")
    fun getMovies(): List<MovieEntity>

    @Query("SELECT * FROM Movies WHERE trackId = :trackId")
    fun observePlanetById(trackId: Int): MovieEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    @Query("DELETE FROM Movies")
    suspend fun deleteMovies()

    @Transaction
    suspend fun setMovies(movies: List<MovieEntity>) {
        deleteMovies()
        movies.forEach { insertMovie(it) }
    }

    @Query("UPDATE Movies SET isFavourite = 1 WHERE trackId = :trackId")
    suspend fun setMovieAsFavorite(trackId: Int) : Int


}