package com.tilenpint.minivideojournalapp

import android.app.Application
import com.tilenpint.minivideojournalapp.di.repositoryModule
import com.tilenpint.minivideojournalapp.di.videoSaverModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import com.tilenpint.minivideojournalapp.di.viewModelModule

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(repositoryModule, viewModelModule, videoSaverModule)
        }
    }
}