package com.missclick.spy.core.advertising

import android.app.Activity
import android.content.Context
import androidx.compose.animation.fadeIn
import androidx.compose.ui.text.intl.Locale
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.missclick.spy.advertising.BuildConfig
import com.unity3d.ads.IUnityAdsLoadListener
import com.unity3d.ads.IUnityAdsShowListener
import com.unity3d.ads.UnityAds
import com.unity3d.ads.UnityAdsShowOptions

class InterstitialAdManagerUnityAndroid(
    private val context: Context,
    private val activity: Lazy<Activity>,
) : InterstitialAdManager {

    var isAdLoadedSuccessfully = false

    init {
        loadAd()
    }

    private fun loadAd() {
        UnityAds.load("Interstitial_Android", object : IUnityAdsLoadListener {
            override fun onUnityAdsAdLoaded(placementId: String?) {
                isAdLoadedSuccessfully = true
            }

            override fun onUnityAdsFailedToLoad(
                placementId: String?,
                error: UnityAds.UnityAdsLoadError?,
                message: String?,
            ) {
                isAdLoadedSuccessfully = false
            }
        })
    }

    override fun showAd(onAdClosed: () -> Unit) {
        if (!isAdLoadedSuccessfully) {
            onAdClosed()
            loadAd()
            return
        }
        UnityAds.show(activity.value, "Interstitial_Android", UnityAdsShowOptions(), object :
            IUnityAdsShowListener {
            override fun onUnityAdsShowFailure(
                placementId: String?,
                error: UnityAds.UnityAdsShowError?,
                message: String?,
            ) {
                onAdClosed()
                loadAd()
            }

            override fun onUnityAdsShowStart(placementId: String?) {

            }

            override fun onUnityAdsShowClick(placementId: String?) {

            }

            override fun onUnityAdsShowComplete(
                placementId: String?,
                state: UnityAds.UnityAdsShowCompletionState?,
            ) {
                onAdClosed()
                loadAd()
            }
        })
    }

}