package com.missclick.spy.core.advertising

import android.app.Activity
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.applovin.mediation.MaxAdFormat
import com.applovin.mediation.ads.MaxAdView
import com.google.android.gms.ads.AdFormat
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.missclick.spy.advertising.BuildConfig
import org.koin.compose.koinInject

@Composable
actual fun BottomAds(
    modifier: Modifier,
) {

    val activity = LocalContext.current as Activity
    val resolver = koinInject<AdsProviderResolver>()
    val provider = remember { resolver.resolve() }

    when (provider) {
        AdsProvider.APPLOVIN ->
            AndroidView(
                modifier = modifier.fillMaxWidth().height(50.dp),
                factory = {
                    MaxAdView(BuildConfig.APP_LOVIN_BANNER_ID).apply {
                        loadAd()
                    }
                }
            )

        AdsProvider.ADMOB ->
            AndroidView(
                modifier = modifier.fillMaxWidth().height(50.dp),
                factory = {
                    AdView(activity).apply {
                        setAdSize(AdSize.BANNER)
                        adUnitId = BuildConfig.ADMOB_BANNER_ID
                        loadAd(AdRequest.Builder().build())
                    }
                }
            )
    }
}