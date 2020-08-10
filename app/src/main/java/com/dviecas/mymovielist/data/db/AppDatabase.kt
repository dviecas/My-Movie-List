package com.dviecas.mymovielist.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dviecas.mymovielist.data.db.dao.MovieDao
import com.dviecas.mymovielist.data.db.model.MovieDto


@Database(entities = [MovieDto::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}