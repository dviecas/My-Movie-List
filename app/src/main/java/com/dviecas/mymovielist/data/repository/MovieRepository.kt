package com.dviecas.mymovielist.data.repository

import com.dviecas.mymovielist.data.api.OMDbService
import com.dviecas.mymovielist.data.api.model.toMovie
import com.dviecas.mymovielist.data.db.dao.MovieDao
import com.dviecas.mymovielist.data.db.model.MovieDto
import com.dviecas.mymovielist.data.db.model.toMovie
import com.dviecas.mymovielist.ui.model.Movie
import com.dviecas.mymovielist.ui.model.toDto
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject


class MovieRepository @Inject constructor(
    private val omDbService: OMDbService,
    private val movieDao: MovieDao
) {

    fun getMovies(): Single<List<Movie>> {
        return omDbService.getMovies()
            .flatMap { apiMovies -> Single.just(apiMovies.map { it.toMovie() }) }
            .flatMap({ movieDao.getFavoriteMovies() }) { movies, movieDtos ->
                Pair<List<Movie>, List<MovieDto>>(movies, movieDtos)
            }
            .flatMap { pair ->
                // Mark favorite movies
                Single.just(pair.first.map {
                    for (dto in pair.second) {
                        if (it.id == dto.id) {
                            it.favorite = true
                        }
                    }
                    it
                })
            }
    }

    fun getFavoriteMovies(): Single<List<Movie>> {
        return movieDao.getFavoriteMovies()
            .flatMap { movieDtos -> Single.just(movieDtos
                .map { it.toMovie().apply { favorite = true; expanded = true } }) }
    }

    fun saveFavoriteMovie(movie: Movie) = movieDao.insertFavoriteMovie(movie.toDto())

    fun deleteFavoriteMovie(movie: Movie) = movieDao.removeFavoriteMovie(movie.toDto())
}