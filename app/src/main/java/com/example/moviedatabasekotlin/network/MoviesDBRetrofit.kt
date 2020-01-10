package com.example.moviedatabasekotlin.network

import com.example.moviedatabasekotlin.model.RepoResultQuery
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesDBRetrofit {

    @GET("/3/search/movie")
    abstract fun getMovies(
        @Query("api_key") apiKey: String,
        @Query("query") keyWords: String
    ): Call<RepoResultQuery>


}