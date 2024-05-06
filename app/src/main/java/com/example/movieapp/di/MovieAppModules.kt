package com.example.movieapp.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.movieapp.BuildConfig
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.data.repository.MovieRepositoryImpl
import com.example.movieapp.data.sources.local.LocalDataSource
import com.example.movieapp.data.sources.local.LocalDataSourceImpl
import com.example.movieapp.data.sources.local.MovieDatabase
import com.example.movieapp.data.sources.remote.MovieApi
import com.example.movieapp.data.sources.remote.RemoteDataSource
import com.example.movieapp.data.sources.remote.RemoteDataSourceImpl
import com.example.movieapp.data.sources.remote.retrofit.createAppApiClient
import com.example.movieapp.domain.AddFavouriteUseCase
import com.example.movieapp.ui.planetDetail.MovieDetailViewModel
import com.example.movieapp.ui.planetList.MovieListViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Singleton
    @Provides
    fun provideAddPlanetUseCase(
        repository: MovieRepository
    ): AddFavouriteUseCase {
        return AddFavouriteUseCase(repository)
    }
}


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providePlanetsRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource,
    ): MovieRepository {
        return MovieRepositoryImpl(localDataSource, remoteDataSource)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    /*    @Singleton
        @Provides
        fun provideBookApi() : MovieApi{
            return Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BooksApi::class.java)
        }*/

    @Provides
    @Singleton
    @Named("base_url")
    fun provideAuthBaseUrl(): String {
        return BuildConfig.BASE_URL
    }

    @Provides
    @Singleton
    fun provideMovieApi(@Named("base_url") BaseUrl: String): MovieApi {
        val mainApiClient = createAppApiClient(BaseUrl)
        return mainApiClient.create(MovieApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRemoteDataSource(api: MovieApi): RemoteDataSource {
        return RemoteDataSourceImpl(api)
    }

    @Singleton
    @Provides
    fun provideLocalDataSource(
        database: MovieDatabase,
    ): LocalDataSource {
        return LocalDataSourceImpl(database.moviesDao())
    }

}


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            MovieDatabase::class.java,
            "Planets.db"
        ).build()
    }
}