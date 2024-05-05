package com.example.movieapp.data.models

import com.example.movieapp.domain.model.Movie
import com.google.gson.annotations.SerializedName

data class MovieResponseData(

    @field:SerializedName("trackId")
    val trackId: Int,

    @field:SerializedName("trackName")
    val trackName: String,

    @field:SerializedName("artworkUrl100")
    val artworkUrl100: String,

    @field:SerializedName("trackPrice")
    val trackPrice: Double,

    @field:SerializedName("primaryGenreName")
    val primaryGenreName: String,

    @field:SerializedName("longDescription")
    val longDescription: String
) {
    fun toMovie(): Movie {
        return Movie(
            trackId = trackId,
            trackName = trackName,
            artworkUrl100 = artworkUrl100,
            primaryGenreName = primaryGenreName,
            longDescription = longDescription,
            trackPrice = trackPrice,
            isFavourite = false
        )
    }

}