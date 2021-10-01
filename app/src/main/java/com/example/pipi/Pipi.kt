package com.example.pipi

import android.app.Application
import com.example.pipi.di.pipiModules
import com.example.pipi.di.viewmodelModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class Pipi:Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        startKoin {
            androidContext(this@Pipi)
            modules(pipiModules)
        }
    }

}