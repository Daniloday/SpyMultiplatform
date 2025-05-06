package com.missclick.spy.core.advertising

import android.app.Activity
import android.location.Location
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.missclick.spy.advertising.BuildConfig
import com.unity3d.services.banners.BannerView
import com.unity3d.services.banners.UnityBannerSize

@Composable
actual fun BottomAds(
    modifier: Modifier,
) {

    val activity = LocalContext.current as Activity

    AndroidView(
        modifier = modifier.fillMaxWidth(),
        factory = { context ->
            if (Locale.current.region.lowercase() == "ru") {
                BannerView(activity, "Banner_Android", UnityBannerSize.standard).apply {
                    load()
                }
            } else {
                AdView(context).apply {
                    setAdSize(AdSize.BANNER)
                    adUnitId = BuildConfig.ADMOB_BANNER_ID
                    loadAd(AdRequest.Builder().build())
                }
            }
        }
    )
}