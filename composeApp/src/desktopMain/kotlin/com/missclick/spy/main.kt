package com.missclick.spy

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.missclick.spy.di.appModule
import org.koin.core.context.startKoin

fun main() {
    startKoin {
        modules(appModule)
    }
    return application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Spy",
        ) {
            App()
        }
    }
}