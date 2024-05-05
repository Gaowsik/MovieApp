package com.example.movieapp.data.sources.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movieapp.domain.model.Movie

@Entity(tableName = "Movies")
data class MovieEntity(
    @PrimaryKey var trackId: Int,

    var trackName: String = "",

    var artworkUrl100: String = "",

    var trackPrice: Double = 0.0,

    var primaryGenreName: String = "",

    var longDescription: String = "",

    var isFavourite: Boolean = false
) {
    fun toMovie(): Movie {
        return Movie(
            trackId = trackId,
            trackName = trackName,
            artworkUrl100 = artworkUrl100,
            primaryGenreName = primaryGenreName,
            longDescription = longDescription,
            trackPrice = trackPrice,
            isFavourite = isFavourite
        )
    }
}

fun Movie.toMovieEntity(): MovieEntity {
    return MovieEntity(
        trackId = trackId,
        artworkUrl100 = artworkUrl100,
        trackName = trackName,
        primaryGenreName = primaryGenreName,
        longDescription = longDescription,
        trackPrice = trackPrice,
        isFavourite = isFavourite
    )
}