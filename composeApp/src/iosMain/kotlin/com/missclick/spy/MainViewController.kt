package com.missclick.spy

import androidx.compose.ui.window.ComposeUIViewController
import com.missclick.spy.core.advertising.AdMobIos
import com.missclick.spy.di.appModule
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.context.startKoin
import org.koin.dsl.module
import platform.UIKit.NSDefaultAttributesDocumentAttribute
import platform.UIKit.UIViewController


fun MainViewController(
    adMobIos: AdMobIos
) = ComposeUIViewController(
    configure = {
        initKoin(adMobIos)
    }
) {
    App()
}

private fun initKoin(
    adMobIos: AdMobIos
) {
    startKoin {
        modules(
            appModule,
            module {
                single { adMobIos }
            }
        )
    }
}

