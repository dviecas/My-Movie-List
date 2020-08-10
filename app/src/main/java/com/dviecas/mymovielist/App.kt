package com.dviecas.mymovielist

import android.app.Application
import com.dviecas.mymovielist.di.AppComponent
import com.dviecas.mymovielist.di.DaggerAppComponent
import timber.log.Timber


class App : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.factory().withContext(applicationContext)
        Timber.plant(Timber.DebugTree())
    }

    fun getAppComponent() = appComponent
}