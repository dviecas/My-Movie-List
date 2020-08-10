package com.dviecas.mymovielist.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dviecas.mymovielist.data.api.OMDbService
import com.dviecas.mymovielist.data.db.AppDatabase
import com.dviecas.mymovielist.data.db.dao.MovieDao
import com.dviecas.mymovielist.util.OMDB_API_URL
import dagger.Module
import dagger.Provides
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module(includes = [ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideOMDbService(): OMDbService {
        return Retrofit.Builder()
            .baseUrl(OMDB_API_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(OMDbService::class.java)
    }

    @Singleton
    @Provides
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "movie-db").build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(db: AppDatabase): MovieDao {
        return db.movieDao()
    }
}