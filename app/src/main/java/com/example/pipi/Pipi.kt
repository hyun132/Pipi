package com.example.pipi

import android.app.Application
import com.example.pipi.di.viewmodelModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Pipi:Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Pipi)
            modules(viewmodelModules)
        }
    }

}