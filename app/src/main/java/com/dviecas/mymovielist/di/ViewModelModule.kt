package com.dviecas.mymovielist.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dviecas.mymovielist.ui.favoritemovies.FavoriteMoviesViewModel
import com.dviecas.mymovielist.ui.movielist.MovieListViewModel
import com.dviecas.mymovielist.util.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule  {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MovieListViewModel::class)
    abstract fun bindMovieListViewModel(movieListViewModel: MovieListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteMoviesViewModel::class)
    abstract fun bindFavoriteMoviesViewModel(favoriteMoviesViewModel: FavoriteMoviesViewModel): ViewModel
}