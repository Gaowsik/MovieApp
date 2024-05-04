package com.example.movieapp.di

import com.example.movieapp.BuildConfig
import com.example.movieapp.data.sources.remote.MovieApi
import com.example.movieapp.data.sources.remote.RemoteDataSource
import com.example.movieapp.data.sources.remote.RemoteDataSourceImpl
import com.example.movieapp.data.sources.remote.retrofit.createAppApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

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

}