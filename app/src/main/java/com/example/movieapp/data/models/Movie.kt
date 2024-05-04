package com.example.movieapp.data.models

import com.google.gson.annotations.SerializedName

data class Movie(
    @field:SerializedName("trackId")
    val trackId: Long,

    @field:SerializedName("trackName")
    val trackName: String,

    @field:SerializedName("artworkUrl100")
    val artworkUrl100: String,

    @field:SerializedName("trackPrice")
    val trackPrice: Double,

    @field:SerializedName("primaryGenreName")
    val primaryGenreName: String,

    @field:SerializedName("longDescription")
    val longDescription: String,

    )
