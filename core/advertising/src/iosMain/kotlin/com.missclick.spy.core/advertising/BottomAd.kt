package com.missclick.spy.core.advertising

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.interop.UIKitViewController
import androidx.compose.ui.unit.dp
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.compose.koinInject


@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun BottomAds(
    modifier: Modifier,
) {

//    val adMobIos: AdMobIos = koinInject()
//
//    UIKitViewController(
//        modifier = Modifier.height(50.dp).width(320.dp).background(Color.Transparent),
//        factory = {
//
//            adMobIos.bannerViewController()
//        }
//    )
}