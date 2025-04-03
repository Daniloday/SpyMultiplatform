package com.missclick.spy.core.advertising

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.missclick.spy.advertising.BuildConfig

internal class InterstitialAdManagerAndroid(
    private val context: Context,
    private val activity: Lazy<Activity>
): InterstitialAdManager {

    private var interstitialAd: InterstitialAd? = null

    init {
        loadAd()
    }

    private fun loadAd() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(context, BuildConfig.ADMOB_REWARDED_INTERSTITIAL_ID, adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdLoaded(ad: InterstitialAd) {
                interstitialAd = ad
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                interstitialAd = null
            }
        })
    }

    override fun showAd(onAdClosed: () -> Unit) {
        if (interstitialAd != null) {
            interstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    onAdClosed()
                    interstitialAd = null
                    loadAd()
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    interstitialAd = null
                }

                override fun onAdShowedFullScreenContent() {
                    interstitialAd = null
                }
            }
            interstitialAd?.show(activity.value)
        } else{
            onAdClosed()
            loadAd()
        }
    }
}
