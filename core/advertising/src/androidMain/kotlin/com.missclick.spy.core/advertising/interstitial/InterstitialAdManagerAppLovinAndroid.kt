package com.missclick.spy.core.advertising.interstitial

import android.app.Activity
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxError
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.ads.MaxInterstitialAd
import com.missclick.spy.advertising.BuildConfig
import com.missclick.spy.core.advertising.InterstitialAdManager

internal class InterstitialAdManagerAppLovinAndroid(
    private val activity: Activity,
) : InterstitialAdManager, MaxAdListener {

    private val interstitialAd = MaxInterstitialAd(BuildConfig.APP_LOVIN_INTERSTITIAL_ID).apply {
        setListener(this@InterstitialAdManagerAppLovinAndroid)
    }

    private var onClose: (() -> Unit)? = null

    private var retryAttempt = 0

    init {
        loadAd()
    }

    private fun loadAd() {
        interstitialAd.loadAd()
    }

    override fun showAd(onAdClosed: () -> Unit) {
        onClose = onAdClosed

        if (interstitialAd.isReady) {
            interstitialAd.showAd(activity)
        } else {
            onClose?.invoke()
            onClose = null
            loadAd()
        }
    }

    // --- MaxAdListener ---

    override fun onAdLoaded(ad: MaxAd) {
        retryAttempt = 0
    }

    override fun onAdLoadFailed(adUnitId: String, error: MaxError) {
        retryAttempt++
        val delaySec = minOf(60, 1 shl retryAttempt)

        activity.window?.decorView?.postDelayed(
            { interstitialAd.loadAd() },
            delaySec * 1_000L
        )
    }

    override fun onAdDisplayed(ad: MaxAd) {}

    override fun onAdDisplayFailed(ad: MaxAd, error: MaxError) {
        onClose?.invoke()
        onClose = null
        interstitialAd.loadAd()
    }

    override fun onAdHidden(ad: MaxAd) {
        onClose?.invoke()
        onClose = null
        interstitialAd.loadAd()
    }

    override fun onAdClicked(ad: MaxAd) {}

}
