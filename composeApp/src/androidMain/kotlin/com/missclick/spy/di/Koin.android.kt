package com.missclick.spy.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

fun startKoin(context: Context) {
    startKoin {
        modules(appModule)
        androidContext(context)
    }
}