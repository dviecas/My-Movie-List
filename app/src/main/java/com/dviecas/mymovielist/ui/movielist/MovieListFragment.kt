package com.dviecas.mymovielist.ui.movielist

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dviecas.mymovielist.App
import com.dviecas.mymovielist.R
import com.dviecas.mymovielist.databinding.FragmentMovieListBinding
import com.dviecas.mymovielist.util.ViewModelFactory
import com.dviecas.mymovielist.util.viewBinding
import javax.inject.Inject


class MovieListFragment : Fragment(R.layout.fragment_movie_list) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val viewModel by viewModels<MovieListViewModel> { viewModelFactory }

    private val binding by viewBinding(FragmentMovieListBinding::bind)

    private lateinit var adapter: MovieAdapter

    override fun onAttach(context: Context) {
        (context.applicationContext as App).getAppComponent().inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MovieAdapter() { movie, position ->
            viewModel.saveOrDeleteFavoriteMovie(movie)
            movie.favorite = !movie.favorite
            adapter.notifyItemChanged(position)
        }
        binding.fmlMovieList.adapter = adapter
        binding.fmlMovieList.layoutManager = LinearLayoutManager(requireContext())

        viewModel.movies.observe(viewLifecycleOwner, Observer { movies ->
            if (movies.isNotEmpty()) {
                adapter.submitMovieList(movies)
            } else {
                showEmptyView()
            }
        })

        viewModel.loadMovies()
    }

    private fun showEmptyView() {}
}