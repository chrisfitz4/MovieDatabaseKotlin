package com.example.moviedatabasekotlin.view

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedatabasekotlin.R
import com.example.moviedatabasekotlin.adapter.MyRVAdapter
import com.example.moviedatabasekotlin.model.Movie
import com.example.moviedatabasekotlin.model.RepoResultQuery
import com.example.moviedatabasekotlin.modelview.MyViewModel
import com.example.moviedatabasekotlin.network.MovieDBRetrofitInstance
import com.example.moviedatabasekotlin.util.Constants
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MyRVAdapter.MyInterface{


    var movies :ArrayList<Movie> = ArrayList<Movie>()
    lateinit var adapter : MyRVAdapter
    lateinit var fragment: Fragment
    lateinit var viewModel: MyViewModel
    lateinit var myObserver: Observer<RepoResultQuery>
    lateinit var instance: MovieDBRetrofitInstance



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.sharedElementEnterTransition =
            TransitionInflater.from(this).inflateTransition(R.transition.shared_element_trans)
        viewModel = ViewModelProviders.of(this).get(MyViewModel::class.java)

        myObserver = Observer{ repoResultQuery: RepoResultQuery ->
                movies = repoResultQuery.movies
                Log.d("TAG_X", "onChanged: " + movies.size)
                setUpRV()
            }

        viewModel.liveData.observe(this, myObserver)
        setUpRV()
        send_query_button.setOnClickListener{
            Log.d("TAG_X", "onClick: ")
            movies.clear()
            val toSearch:String = my_query_editText.text.toString()
            if(toSearch.isEmpty())
                viewModel.getRepo(toSearch)
            else
                viewModel.getRepo(toSearch)

            (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                send_query_button.getWindowToken(),
                0
            )
        }
    }

    private fun setUpRV() {
        adapter = MyRVAdapter(this.applicationContext, movies, this)
        Log.d("TAG_X",""+adapter.movies)
        listMovies_rv.adapter = (adapter)
        listMovies_rv.layoutManager=(LinearLayoutManager(this))
    }


    override fun startFrag(movie: Movie, view: ImageView) {
        Log.d("TAG_X", "transitionImage: " + movie.getTitle())
        val bundle = Bundle()
        bundle.putParcelable(Constants.RETRIEVE_MOVIE_KEY, movie)
        bundle.putString("test", "test success")
        Log.d("TAG_X", "transitionImage: " + view.transitionName)
        val manager = supportFragmentManager
        fragment = manager.findFragmentByTag(Constants.FRAGMENT_TAG)?:ViewMovieFrag()
        fragment.arguments = bundle

        manager
            .beginTransaction()
            .addToBackStack(Constants.FRAGMENT_TAG)
            .addSharedElement(view, Constants.TRANSITION_NAME_BASE + movie.getId())
            .add(R.id.frame_layout, fragment, Constants.FRAGMENT_TAG)
            .commit()
    }
}


