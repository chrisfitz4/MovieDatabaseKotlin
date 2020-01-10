package com.example.moviedatabasekotlin.modelview

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.moviedatabasekotlin.model.RepoResultQuery
import com.example.moviedatabasekotlin.network.MovieDBRetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyViewModel(application: Application): AndroidViewModel(application) {

    var liveData: MutableLiveData<RepoResultQuery> = MutableLiveData<RepoResultQuery>()
    var instance: MovieDBRetrofitInstance = MovieDBRetrofitInstance()


    fun getRepo(toSearch: String) {

        instance.getMovies(toSearch).enqueue(object : Callback<RepoResultQuery> {
            override fun onResponse(
                call: Call<RepoResultQuery>,
                response: Response<RepoResultQuery>
            ) {
                Log.d("TAG_X","onResponse: ")
                var name = ""
                if (response.body()!!.movies.size.equals(0)) {
                    name = response.body()!!.movies[0].getTitle()
                }
                liveData.value = response.body()
                Log.d("TAG_X", "onResponse: $name")
            }

            override fun onFailure(call: Call<RepoResultQuery>, t: Throwable) {
                Log.d("TAG_X", "onFailure: " + t.message)
                Log.d("TAG_X", "onFailure: $call")
            }
        })
    }

}