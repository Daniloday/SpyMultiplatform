package com.missclick.spy.core.advertising.rewarded

import android.app.Activity
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxError
import com.applovin.mediation.MaxReward
import com.applovin.mediation.MaxRewardedAdListener
import com.applovin.mediation.ads.MaxRewardedAd
import com.missclick.spy.advertising.BuildConfig
import com.missclick.spy.core.advertising.RewardedAdManager

internal class RewardedAdManagerAppLovinAndroid(
    private val activity: Activity,
) : RewardedAdManager, MaxRewardedAdListener {

    private val rewardedAd = MaxRewardedAd.getInstance(BuildConfig.APP_LOVIN_REWARDED_ID).apply {
        setListener(this@RewardedAdManagerAppLovinAndroid)
    }

    private var onSkipped: (() -> Unit)? = null
    private var onWatched: (() -> Unit)? = null

    private var rewardEarned = false
    private var retryAttempt = 0

    init {
        loadAd()
    }

    private fun loadAd() {
        rewardedAd.loadAd()
    }

    override fun showAd(onAdSkipped: () -> Unit, onAdWatched: () -> Unit) {
        this.onSkipped = onAdSkipped
        this.onWatched = onAdWatched
        rewardEarned = false

        if (rewardedAd.isReady) {
            rewardedAd.showAd(activity)
        } else {
            onSkipped?.invoke()
            clearCallbacks()
            loadAd()
        }
    }

    // --- Load callbacks ---

    override fun onAdLoaded(ad: MaxAd) {
        retryAttempt = 0
    }

    override fun onAdLoadFailed(adUnitId: String, error: MaxError) {
        retryAttempt++
        val delaySec = minOf(60, 1 shl retryAttempt)

        activity.window.decorView.postDelayed(
            { rewardedAd.loadAd() },
            delaySec * 1_000L
        )
    }

    // --- Show callbacks ---

    override fun onAdDisplayed(ad: MaxAd) {}

    override fun onAdDisplayFailed(ad: MaxAd, error: MaxError) {
        onSkipped?.invoke()
        clearCallbacks()
        rewardedAd.loadAd()
    }

    override fun onAdHidden(ad: MaxAd) {
        if (rewardEarned) onWatched?.invoke() else onSkipped?.invoke()
        clearCallbacks()
        rewardedAd.loadAd()
    }

    override fun onAdClicked(ad: MaxAd) {}

    override fun onUserRewarded(ad: MaxAd, reward: MaxReward) {
        rewardEarned = true
    }

    private fun clearCallbacks() {
        onSkipped = null
        onWatched = null
    }
}
