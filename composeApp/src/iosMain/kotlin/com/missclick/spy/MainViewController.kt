package com.missclick.spy

import androidx.compose.ui.window.ComposeUIViewController
import com.missclick.spy.core.advertising.AdMobIos
import com.missclick.spy.di.appModule
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.context.startKoin
import org.koin.dsl.module
import platform.UIKit.UIViewController


@OptIn(ExperimentalForeignApi::class)
fun MainViewController(
    adMobIos: AdMobIos
) = ComposeUIViewController(
    configure = {
        startKoin {
            modules(
                appModule,
                module {
                    single { adMobIos }
                }
            )
        }
    }

) {
    App()
}

