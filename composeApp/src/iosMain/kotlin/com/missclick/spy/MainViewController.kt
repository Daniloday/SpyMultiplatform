package com.missclick.spy

import androidx.compose.ui.window.ComposeUIViewController
import com.missclick.spy.core.advertising.AdMobIos
import com.missclick.spy.core.advertising.AppLovinIos
import com.missclick.spy.di.appModule
import com.revenuecat.purchases.kmp.LogLevel
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.PurchasesConfiguration
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun MainViewController(
    adMobIos: AdMobIos,
    appLovinIos: AppLovinIos,
) = ComposeUIViewController(
    configure = {
        Purchases.logLevel = LogLevel.DEBUG
        Purchases.configure(PurchasesConfiguration.Builder( "appl_EKJtjzuwVBsXCNUAvhRGQmsXBui").build())
        initKoin(adMobIos, appLovinIos)
    }
) {
    App()
}

private fun initKoin(
    adMobIos: AdMobIos,
    appLovinIos: AppLovinIos,
) {
    startKoin {
        modules(
            appModule,
            module {
                single { adMobIos }
                single { appLovinIos }
            }
        )
    }
}
