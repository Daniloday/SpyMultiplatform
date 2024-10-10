package com.missclick.spy

import android.app.Application
import com.missclick.spy.di.startKoin

class SpyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this)
    }
}