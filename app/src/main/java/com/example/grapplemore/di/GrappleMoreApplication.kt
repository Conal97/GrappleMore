package com.example.grapplemore.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class GrappleMoreApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        // Timber for debugging
        Timber.plant(Timber.DebugTree())
    }
}

