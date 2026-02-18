package com.missclick.spy.core.advertising


internal class InterstitialAdManagerIos(
    private val adMobIos: AdMobIos
): InterstitialAdManager {

    init {
        loadAd()
    }

    private fun loadAd() {
        adMobIos.loadInterstitialAd()
    }

    override fun showAd(onAdClosed: () -> Unit) {
        adMobIos.showInterstitialAd(onAdClosed)
    }
}


