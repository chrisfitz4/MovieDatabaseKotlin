package com.example.moviedatabasekotlin.network

import com.example.moviedatabasekotlin.model.RepoResultQuery
import com.example.moviedatabasekotlin.util.Constants
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieDBRetrofitInstance() {

    private var moviesDBRetrofitInstance: MoviesDBRetrofit

    init{
        moviesDBRetrofitInstance = getInstanceOfMoviesDBRetrofit(getInstance())
    }

    private fun getInstanceOfMoviesDBRetrofit(retrofit: Retrofit): MoviesDBRetrofit {
        return retrofit.create(MoviesDBRetrofit::class.java)
    }

    private fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getMovies(toSearch: String): Call<RepoResultQuery> {
        return moviesDBRetrofitInstance.getMovies(Constants.MY_API_KEY, toSearch)
    }

}
