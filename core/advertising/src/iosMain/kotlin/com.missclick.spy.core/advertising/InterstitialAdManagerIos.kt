package com.missclick.spy.core.advertising


internal class InterstitialAdManagerIos(
    private val adMobIos: AdMobIos
): InterstitialAdManager {

    init {
        loadAd()
    }

    private fun loadAd() {
        adMobIos.loadRewardedAd()
    }

    override fun showAd(onAdClosed: () -> Unit) {
        adMobIos.showRewardedAd {
            onAdClosed()
            loadAd()
        }
    }
}
