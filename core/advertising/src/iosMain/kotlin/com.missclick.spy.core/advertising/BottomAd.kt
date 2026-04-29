package com.missclick.spy.core.advertising

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.UIKitViewController
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.compose.koinInject


@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun BottomAds(
    modifier: Modifier,
) {

    val resolver: AdsProviderResolver = koinInject()
    val adMobIos: AdMobIos = koinInject()
    val appLovinIos: AppLovinIos = koinInject()
    val provider = remember { resolver.resolve() }

    UIKitViewController(
        modifier = modifier.fillMaxWidth().height(50.dp).background(Color.Transparent),
        factory = {
            when (provider) {
                AdsProvider.ADMOB -> adMobIos.bannerViewController()
                AdsProvider.APPLOVIN -> appLovinIos.bannerViewController()
            }
        }
    )
}
