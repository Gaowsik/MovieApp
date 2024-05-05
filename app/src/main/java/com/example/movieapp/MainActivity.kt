package com.example.movieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.movieapp.ui.Constants.QUERY_VALUE_COUNTRY
import com.example.movieapp.ui.Constants.QUERY_VALUE_MEDIA
import com.example.movieapp.ui.Constants.QUERY_VALUE_TERM
import com.example.movieapp.ui.planetList.MovieListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val loginViewModel: MovieListViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
      //  loginViewModel.refreshMoviesList(QUERY_VALUE_TERM, QUERY_VALUE_COUNTRY, QUERY_VALUE_MEDIA)
        loginViewModel.addFavouriteById(208510932)
        loginViewModel.getMovies()
    }
}