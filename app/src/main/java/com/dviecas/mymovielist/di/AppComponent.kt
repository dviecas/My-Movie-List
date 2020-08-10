package com.dviecas.mymovielist.di

import android.content.Context
import com.dviecas.mymovielist.ui.favoritemovies.FavoriteMoviesFragment
import com.dviecas.mymovielist.ui.movielist.MovieListFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(fragment: MovieListFragment)
    fun inject(fragment: FavoriteMoviesFragment)

    @Component.Factory
    interface Factory {
        fun withContext(@BindsInstance context: Context): AppComponent
    }
}