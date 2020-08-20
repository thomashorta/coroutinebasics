package com.thomashorta.coroutinebasics

import android.app.Application
import com.thomashorta.coroutinebasics.di.koinModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(koinModule)
        }
    }
}