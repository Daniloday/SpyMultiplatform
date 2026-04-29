package com.missclick.spy.core.advertising

internal class RewardedAdManagerIos(
    private val loadRewardedAd: () -> Unit,
    private val showRewardedAd: (onAdClosed: () -> Unit, onReward: () -> Unit) -> Unit,
): RewardedAdManager {

    init {
        loadAd()
    }

    private fun loadAd() {
        loadRewardedAd()
    }


    override fun showAd(onAdSkipped: () -> Unit, onAdWatched: () -> Unit) {
        showRewardedAd(onAdSkipped, onAdWatched)
    }
}
