package com.dviecas.mymovielist.ui.favoritemovies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dviecas.mymovielist.data.repository.MovieRepository
import com.dviecas.mymovielist.ui.model.Movie
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject


class FavoriteMoviesViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val movies: MutableLiveData<List<Movie>> by lazy {
        MutableLiveData<List<Movie>>()
    }

    fun loadFavoriteMovies() {
        compositeDisposable.add(
            repository.getFavoriteMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { movies, error ->
                    if (movies != null && movies.isNotEmpty()) {
                        this.movies.value = movies
                    } else {
                        this.movies.value = listOf()
                        if (error != null) {
                            Timber.w(error)
                        }
                    }
                }
        )
    }

    fun removeFavoriteMovie(movie: Movie) {
        compositeDisposable.add(
            repository.deleteFavoriteMovie(movie)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({}) { Timber.w(it) }
        )
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()
    }
}