package com.dviecas.mymovielist.ui.favoritemovies

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

class FavoriteMoviesViewModelTest {

    @Rule
    @JvmField
    val trampolineSchedulerRule = TrampolineSchedulerRule()

    @Rule
    @JvmField
    val liveDataImmediateRunnerRule = InstantTaskExecutorRule()

    private lateinit var viewModel: FavoriteMoviesViewModel
    private lateinit var repoMock: MovieRepository
    val movies = listOf(
        Movie(
            "id123",
            "Shutter Island",
            "",
            6.8,
            "http://",
            favorite = true
        )
    )

    @Before
    fun setUp() {
        repoMock = mock {
            on { getFavoriteMovies() } doReturn Single.just(movies)
            on { deleteFavoriteMovie(any()) } doReturn Completable.complete()
        }
        viewModel = FavoriteMoviesViewModel(repoMock)
    }

    @Test
    fun getFavoriteMovies_emitsMovieList() {
        viewModel.loadFavoriteMovies()

        assertEquals(viewModel.movies.getOrAwaitValue(), movies)
    }

    @Test
    fun getFavoriteMovies_emitsEmptyListOnError() {
        whenever(repoMock.getFavoriteMovies()).thenReturn(Single.error(Throwable()))

        viewModel.loadFavoriteMovies()

        assertEquals(viewModel.movies.getOrAwaitValue(), listOf<Movie>())
    }

    @Test
    fun removeFavoriteMovie_delegatesDeletionToRepository() {
        viewModel.removeFavoriteMovie(movies[0])

        verify(repoMock).deleteFavoriteMovie(movies[0])
    }
}