package com.dviecas.mymovielist.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dviecas.mymovielist.data.db.model.MovieDto
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single


@Dao
interface MovieDao {

    @Query("SELECT * FROM movieDto")
    fun getFavoriteMovies(): Single<List<MovieDto>>

    @Insert
    fun insertFavoriteMovie(movie: MovieDto): Completable

    @Delete
    fun removeFavoriteMovie(movie: MovieDto): Completable
}