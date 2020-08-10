package com.dviecas.mymovielist.ui.movielist

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dviecas.mymovielist.R
import com.dviecas.mymovielist.databinding.ItemMovieBinding
import com.dviecas.mymovielist.ui.model.Movie


class MovieAdapter(private val onFavorited: (Movie, Int) -> Unit) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val movieList = mutableListOf<Movie>()

    override fun getItemCount() = movieList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder(
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onClick = {
                movieList[it].expanded = !movieList[it].expanded
                notifyItemChanged(it)
            },
            onFavorited = { onFavorited(movieList[it], it) }
        )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bind(movieList[position])

    fun submitMovieList(movies: List<Movie>) {
        movieList.clear()
        movieList.addAll(movies)
        notifyDataSetChanged()
    }

    fun removeMovie(movie: Movie) = movieList.remove(movie)

    class MovieViewHolder(
        private val itemMovieBinding: ItemMovieBinding,
        private val onClick: (Int) -> Unit,
        private val onFavorited: (Int) -> Unit
    ) : RecyclerView.ViewHolder(itemMovieBinding.root) {

        fun bind(movie: Movie) {
            itemMovieBinding.imMovieTitle.text = movie.name
            itemMovieBinding.imMovieRating.text = movie.rating.toString()
            itemMovieBinding.imMovieDescription.text = movie.description
            Glide.with(itemMovieBinding.root).load(movie.posterUrl)
                .into(itemMovieBinding.imMoviePoster)
            itemMovieBinding.imMovieDescription.visibility = if (movie.expanded) VISIBLE else GONE
            itemMovieBinding.imFavoriteButton.icon =
                if (movie.favorite) itemView.context.getDrawable(R.drawable.ic_baseline_star_24)
                else itemView.context.getDrawable(R.drawable.ic_baseline_star_border_24)
            itemView.setOnClickListener { onClick(adapterPosition) }
            itemMovieBinding.imFavoriteButton.setOnClickListener { onFavorited(adapterPosition) }
        }
    }
}