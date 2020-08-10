package com.dviecas.mymovielist.ui.movielist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dviecas.mymovielist.data.repository.MovieRepository
import com.dviecas.mymovielist.ui.model.Movie
import com.dviecas.mymovielist.util.TrampolineSchedulerRule
import com.dviecas.mymovielist.util.getOrAwaitValue
import com.nhaarman.mockitokotlin2.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MovieListViewModelTest {

    @Rule
    @JvmField
    val trampolineSchedulerRule = TrampolineSchedulerRule()

    @Rule
    @JvmField
    val liveDataImmediateRunnerRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MovieListViewModel
    private lateinit var repoMock: MovieRepository
    val movies = listOf(
        Movie(
            "id123",
            "Shutter Island",
            "",
            6.8,
            "http://",
            favorite = false
        )
    )

    @Before
    fun setUp() {
        repoMock = mock {
            on { getMovies() } doReturn Single.just(movies)
            on { saveFavoriteMovie(any()) } doReturn Completable.complete()
            on { deleteFavoriteMovie(any()) } doReturn Completable.complete()
        }
        viewModel = MovieListViewModel(repoMock)
    }

    @Test
    fun loadMovies_emitsReceivedValue() {
        viewModel.loadMovies()

        assertEquals(viewModel.movies.getOrAwaitValue(), movies)
    }

    @Test
    fun loadMovies_emitsEmptyListOnError() {
        whenever(repoMock.getMovies()).thenReturn(Single.error(Throwable()))

        viewModel.loadMovies()

        assertEquals(viewModel.movies.getOrAwaitValue(), listOf<Movie>())
    }

    @Test
    fun saveOrDeleteFavoriteMovie_savesFavoritedMovie() {
        viewModel.saveOrDeleteFavoriteMovie(movies[0])

        verify(repoMock).saveFavoriteMovie(movies[0])
    }

    @Test
    fun saveOrDeleteFavoriteMovie_deletesUnfavoritedMovie() {
        viewModel.saveOrDeleteFavoriteMovie(movies[0].apply { favorite = true })

        verify(repoMock).deleteFavoriteMovie(movies[0])
    }
}