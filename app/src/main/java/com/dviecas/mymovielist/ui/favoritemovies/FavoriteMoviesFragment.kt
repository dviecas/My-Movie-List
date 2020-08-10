package com.dviecas.mymovielist.ui.favoritemovies

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dviecas.mymovielist.App
import com.dviecas.mymovielist.R
import com.dviecas.mymovielist.databinding.FragmentFavoriteMoviesBinding
import com.dviecas.mymovielist.ui.movielist.MovieAdapter
import com.dviecas.mymovielist.util.ViewModelFactory
import com.dviecas.mymovielist.util.viewBinding
import javax.inject.Inject


class FavoriteMoviesFragment : Fragment(R.layout.fragment_favorite_movies) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val viewModel by viewModels<FavoriteMoviesViewModel> { viewModelFactory }

    private val binding by viewBinding(FragmentFavoriteMoviesBinding::bind)

    private lateinit var adapter: MovieAdapter

    override fun onAttach(context: Context) {
        (context.applicationContext as App).getAppComponent().inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MovieAdapter() { movie, position ->
            viewModel.removeFavoriteMovie(movie)
            adapter.removeMovie(movie)
            adapter.notifyItemRemoved(position)
        }
        binding.ffmMovieList.adapter = adapter
        binding.ffmMovieList.layoutManager = LinearLayoutManager(requireContext())

        viewModel.movies.observe(viewLifecycleOwner, Observer { movies ->
            if (movies.isNotEmpty()) {
                adapter.submitMovieList(movies)
            } else {
                showEmptyView()
            }
        })

        viewModel.loadFavoriteMovies()
    }

    fun showEmptyView() {

    }
}