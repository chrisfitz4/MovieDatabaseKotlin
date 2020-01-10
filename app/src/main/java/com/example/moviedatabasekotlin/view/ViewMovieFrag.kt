package com.example.moviedatabasekotlin.view

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.moviedatabasekotlin.R
import com.example.moviedatabasekotlin.model.Movie
import com.example.moviedatabasekotlin.util.Constants
import kotlinx.android.synthetic.main.single_movie_frag.*

class ViewMovieFrag : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setRetainInstance(true);
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.single_movie_frag, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        val args = this.arguments
        val movie:Movie ?= args?.getParcelable(Constants.RETRIEVE_MOVIE_KEY)

        //attempting to do a shared element transition (unsuccessfully)
        val transitionName = Constants.TRANSITION_NAME_BASE + movie!!.getId()
        movie_icon_frag.setTransitionName(transitionName)
        Log.d("TAG_X", "onViewCreated: $transitionName")
        Glide.with(this)
            .load(Constants.BASE_URL_IMAGE + movie!!.getPosterPath())
            .dontAnimate()
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }
            })
            .into(movie_icon_frag)

        movieTitle_frag.text = (movie.title)
        if (movie.title.length > 20) {
            movieTitle_frag.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f)
        }
        movieDescrip_frag.text = (movie!!.getOverview())
        movieDescrip_frag.movementMethod = (ScrollingMovementMethod())
        movieReleaseDate_frag.text = (movie!!.getReleaseDate())
        movieRating_frag.text  = ("Rating: " + movie!!.getVoteAverage())
        Glide.with(this)
            .load(Constants.BASE_URL_IMAGE + movie.getBackdropPath())
            .into(base_image_frag)
    }

    override fun onDetach() {
        super.onDetach()
        fragmentManager!!.popBackStack()
    }

}