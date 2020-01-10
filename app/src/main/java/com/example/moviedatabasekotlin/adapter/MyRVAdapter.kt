package com.example.moviedatabasekotlin.adapter

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedatabasekotlin.R
import com.example.moviedatabasekotlin.model.Movie
import com.example.moviedatabasekotlin.model.RepoResultQuery
import com.example.moviedatabasekotlin.util.Constants
import kotlinx.android.synthetic.main.rv_layout.view.*
import kotlinx.android.synthetic.main.single_movie_frag.view.*
import kotlinx.android.synthetic.main.single_movie_frag.view.movieTitle_frag
import org.w3c.dom.Text

class MyRVAdapter(var context: Context, var movies: List<Movie>, var anInterface: MyInterface): RecyclerView.Adapter<MyRVAdapter.ViewHolder>(){

    interface MyInterface{
        fun startFrag(movie: Movie, view: ImageView)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.movieTitle_frag
        var icon : ImageView = itemView.movieIcon
        var title2 : TextView = itemView.movieTitle2
        var icon2 : ImageView = itemView.movieIcon2
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRVAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_layout,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size/2
    }

    override fun onBindViewHolder(holder: MyRVAdapter.ViewHolder, position: Int) {
        var titleText: String = movies.get(position*2).getTitle()
        holder.title.setText(titleText)
        holder.title.setTextSize(TypedValue.COMPLEX_UNIT_DIP,setTextTitle(titleText))
        var movieURL : String = movies.get(position*2).getPosterPath()
        movieURL=Constants.BASE_URL_IMAGE+movieURL
        Glide.with(context).load(movieURL)
            .placeholder(R.drawable.icon_android).into(holder.icon)
        holder.icon.setOnClickListener{
            anInterface.startFrag(movies.get(position*2),holder.icon)
        }
        ViewCompat.setTransitionName(holder.icon,Constants.TRANSITION_NAME_BASE+movies.get(position*2).id)
        if(movies.size%2!=0&&position==itemCount){
            holder.title2.visibility=View.INVISIBLE
            holder.icon2.visibility=View.INVISIBLE
        }else{
            val movie2 = movies.get(position*2+1)
            holder.title2.text=movie2.title
            holder.title2.textSize = setTextTitle(movie2.title)
            var movie2URL : String = Constants.BASE_URL_IMAGE+movie2.posterPath
            Glide.with(context).load(movie2URL).placeholder(R.drawable.icon_android).into(holder.icon2)
            holder.icon2.setOnClickListener{
                anInterface.startFrag(movie2,holder.icon2)
            }
            ViewCompat.setTransitionName(holder.icon2,Constants.TRANSITION_NAME_BASE+movie2.id)
        }
    }

    fun setTextTitle(text: String):Float{
        if(text.length>30){
            return 16f
        }else{
            return 24f
        }
    }

}

