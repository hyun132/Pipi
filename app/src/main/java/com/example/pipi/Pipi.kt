package com.example.pipi

import android.app.Application
import com.example.pipi.di.pipiModules
import com.example.pipi.domain.model.Prefs
import com.example.pipi.presentation.main.Member
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class Pipi : Application() {


    /**
     * TODO : 본인 계정 정보를 어디에 들고 있을지 결정해야함. 여기에 두는게 좋은 방법인지 생각해볼것.
     */
    lateinit var MyInfo: Member
    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(applicationContext)
        Timber.plant(Timber.DebugTree())
        startKoin {
            androidContext(this@Pipi)
            modules(pipiModules)
        }
    }

    companion object {
        lateinit var prefs: Prefs
    }

}