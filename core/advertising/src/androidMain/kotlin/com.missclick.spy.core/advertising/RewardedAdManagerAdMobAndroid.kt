package com.missclick.spy.core.advertising

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.missclick.spy.advertising.BuildConfig

internal class RewardedAdManagerAdMobAndroid(
    private val context: Context,
    private val activity: Activity,
): RewardedAdManager {

    private var rewardedAd: RewardedAd? = null

    init {
        loadAd()
    }

    private fun loadAd() {
        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(context, BuildConfig.ADMOB_REWARDED_ID, adRequest, object : RewardedAdLoadCallback() {
            override fun onAdLoaded(ad: RewardedAd) {
                rewardedAd = ad
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                rewardedAd = null
            }
        })
    }

    override fun showAd(onAdSkipped: () -> Unit, onAdWatched: () -> Unit) {
        if (rewardedAd != null) {
            rewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    onAdSkipped()
                    rewardedAd = null
                    loadAd()
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    rewardedAd = null
                }

                override fun onAdShowedFullScreenContent() {
                    rewardedAd = null
                }
            }
            rewardedAd?.show(activity) { onAdWatched() }
        } else {
            onAdSkipped()
            loadAd()
        }
    }
}