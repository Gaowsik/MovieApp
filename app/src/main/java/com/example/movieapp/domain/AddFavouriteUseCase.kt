package com.example.movieapp.domain

import com.example.movieapp.data.repository.MovieRepository
import javax.inject.Inject

class AddFavouriteUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    suspend operator fun invoke(id: Int) : Int {
        if (id==0) {
            throw Exception("Id is Empty")
        }
        return movieRepository.addFavouriteById(id)
    }
}