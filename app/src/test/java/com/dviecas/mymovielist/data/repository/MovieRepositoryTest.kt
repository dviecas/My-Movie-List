package com.dviecas.mymovielist.data.repository

import com.dviecas.mymovielist.data.api.OMDbService
import com.dviecas.mymovielist.data.api.model.ApiMovie
import com.dviecas.mymovielist.data.db.dao.MovieDao
import com.dviecas.mymovielist.data.db.model.MovieDto
import com.dviecas.mymovielist.ui.model.Movie
import com.dviecas.mymovielist.util.TrampolineSchedulerRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class MovieRepositoryTest {

    @Rule
    @JvmField
    val trampolineSchedulerRule = TrampolineSchedulerRule()
    private lateinit var repository: MovieRepository
    private lateinit var omdbMock: OMDbService
    private lateinit var movieDaoMock: MovieDao

    val apiMovies = listOf(
        ApiMovie(
            "Shutter Island",
            "",
            6.8,
            "http://",
            "id123"
        ), ApiMovie(
            "Fight Club",
            "",
            8.2,
            "http://",
            "id124"
        )
    )
    val movieDtos = listOf(
        MovieDto(
            "id124",
            "Fight Club",
            "",
            8.2,
            "http://"
        )
    )
    val expectedMovies = listOf(
        Movie(
            "id123",
            "Shutter Island",
            "",
            6.8,
            "http://",
            favorite = false
        ),
        Movie(
            "id124",
            "Fight Club",
            "",
            8.2,
            "http://",
            favorite = true
        )
    )


    @Before
    fun setUp() {
        omdbMock = mock {
            on { getMovies() } doReturn Single.just(apiMovies)
        }
        movieDaoMock = mock {
            on { getFavoriteMovies() } doReturn Single.just(movieDtos)
            on { insertFavoriteMovie(any()) } doReturn Completable.complete()
            on { removeFavoriteMovie(any()) } doReturn Completable.complete()
        }

        repository = MovieRepository(omdbMock, movieDaoMock)
    }

    @Test
    fun getMovies_correctlyMapsRemoteAndLocalMovies() {
        val testObserver = repository.getMovies().test()

        testObserver
            .assertComplete()
            .assertValue(expectedMovies)
            .dispose()
    }

    @Test
    fun getFavoriteMovies_CorrectlyMapsAndMarksAsExpanded() {
        val testObserver = repository.getFavoriteMovies().test()

        testObserver
            .assertComplete()
            .assertValue(listOf(expectedMovies[1].apply { expanded = true }))
            .dispose()
    }

    @Test
    fun saveFavoriteMovie_mapsToDtoAndDelegetesSavingToDao() {
        val testObserver = repository.saveFavoriteMovie(expectedMovies[1]).test()

        testObserver
            .assertComplete()
            .dispose()
        verify(movieDaoMock).insertFavoriteMovie(movieDtos[0])
    }

    @Test
    fun deleteFavoriteMovie_mapsToDtoAndDelegatesDeletingToDao() {
        val testObserver = repository.deleteFavoriteMovie(expectedMovies[1]).test()

        testObserver
            .assertComplete()
            .dispose()
        verify(movieDaoMock).removeFavoriteMovie(movieDtos[0])
    }
}