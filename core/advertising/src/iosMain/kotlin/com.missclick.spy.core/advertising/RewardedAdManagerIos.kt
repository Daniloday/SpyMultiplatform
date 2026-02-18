package com.missclick.spy.core.advertising

internal class RewardedAdManagerIos(
    private val adMobIos: AdMobIos
): RewardedAdManager {

    init {
        loadAd()
    }

    private fun loadAd() {
        adMobIos.loadRewardedAd()
    }


    override fun showAd(onAdSkipped: () -> Unit, onAdWatched: () -> Unit) {
        adMobIos.showRewardedAd(onAdSkipped, onAdWatched)
    }
}