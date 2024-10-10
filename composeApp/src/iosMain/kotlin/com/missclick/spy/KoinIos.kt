package com.missclick.spy

import com.missclick.spy.di.appModule
import org.koin.core.context.startKoin

fun StartKoin() {
    startKoin {
        modules(appModule)
    }
}