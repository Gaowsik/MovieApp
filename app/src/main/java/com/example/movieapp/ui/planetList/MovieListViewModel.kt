package com.example.movieapp.ui.planetList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.data.utills.WorkResult
import com.example.movieapp.data.utills.state_models.Resource
import com.example.movieapp.data.utills.state_models.setErrorString
import com.example.movieapp.data.utills.state_models.setLoading
import com.example.movieapp.data.utills.state_models.setSuccess
import com.example.movieapp.domain.AddFavouriteUseCase
import com.example.movieapp.ui.Constants.ACTION_SEARCH_DELAY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val addFavouriteUseCase: AddFavouriteUseCase
) : ViewModel() {
    lateinit var myMoviesListLiveData: MutableLiveData<Resource<List<Movie>>>
    lateinit var filteredMoviesLiveData: MutableLiveData<Resource<List<Movie>>>
    lateinit var isRefreshedLiveData: MutableLiveData<Resource<Boolean>>
    private val _internalMoviesListLiveData = MutableLiveData<Resource<List<Movie>>>()


    init {
        initStateLiveData()
    }

    private fun initStateLiveData() {
        myMoviesListLiveData = MutableLiveData<Resource<List<Movie>>>()
        filteredMoviesLiveData = MutableLiveData<Resource<List<Movie>>>()
        isRefreshedLiveData = MutableLiveData<Resource<Boolean>>()
    }


/*    fun getMovies() {
        myMoviesListLiveData.setLoading()
        viewModelScope.launch {

            movieRepository.getMovies()
                .catch {
                    myMoviesListLiveData.setErrorString(it.message.toString())
                }
                .collect {
                    if (it is WorkResult.Success) {
                        myMoviesListLiveData.setSuccess(it.data)
                    }
                }
        }
    }*/


    val settingsUiState: StateFlow<MainActivityUiState> =
        movieRepository.getMovies()
            .catch {
                MainActivityUiState.Error(it)
            }
            .map { result ->
                when (result) {
                    is WorkResult.Success -> MainActivityUiState.Success(result.data)
                    is WorkResult.Error -> MainActivityUiState.Error(result.exception)
                    is WorkResult.Loading -> MainActivityUiState.Loading(true)
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5.seconds.inWholeMilliseconds),
                initialValue = MainActivityUiState.Loading(true),
            )

    /**
     * this is created to get the latest db value
     */
    fun getLatestMovies(
    ) = viewModelScope.launch {
        _internalMoviesListLiveData.setLoading()
        movieRepository.getMovies()
            .catch {
                _internalMoviesListLiveData.setErrorString(it.message.toString())
            }
            .collect {
                if (it is WorkResult.Success) {
                    _internalMoviesListLiveData.setSuccess(it.data)
                }

            }
    }

    fun addFavouriteById(id: Int) = viewModelScope.launch {
        val updateTaxiStatusResult =
            addFavouriteUseCase(id)
        if (updateTaxiStatusResult != 1) throw IllegalStateException("Failed to On Local!")
    }


    fun refreshMoviesList(
        term: String, country: String, media: String
    ) = viewModelScope.launch {
        isRefreshedLiveData.setLoading()
        try {
            movieRepository.refreshMovies(term, country, media)
            isRefreshedLiveData.setSuccess(true)
        } catch (e: Exception) {
            isRefreshedLiveData.setErrorString((e.toString()))
            e.printStackTrace()
            return@launch
        }
    }

    fun filterMovies(query: String) {
        viewModelScope.launch {
            delay(ACTION_SEARCH_DELAY)
            filteredMoviesLiveData.setLoading()

            val moviesJob = async { getLatestMovies() }
            // Wait for the getMovies job to complete
            moviesJob.await()
            val currentList =
                _internalMoviesListLiveData.value?.data ?: emptyList()
            val filteredList = if (query.isBlank()) {
                currentList
            } else {
                currentList.filter { movie ->
                    movie.trackName.contains(query, true)
                }
            }

            if (filteredList.isNotEmpty()) {
                filteredMoviesLiveData.setSuccess(filteredList, message = null)
            } else {
                filteredMoviesLiveData.setErrorString("No results found")
            }
        }
    }

    sealed interface MainActivityUiState {
         data class Loading(val isLoading : Boolean) : MainActivityUiState
        data class Success(val listMovies: List<Movie>) : MainActivityUiState
        data class Error(val exception: Throwable): MainActivityUiState
    }


}