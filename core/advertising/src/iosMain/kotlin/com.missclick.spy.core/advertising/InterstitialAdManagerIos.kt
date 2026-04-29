package com.missclick.spy.core.advertising


internal class InterstitialAdManagerIos(
    private val loadInterstitialAd: () -> Unit,
    private val showInterstitialAd: (onAdClosed: () -> Unit) -> Unit,
): InterstitialAdManager {

    init {
        loadAd()
    }

    private fun loadAd() {
        loadInterstitialAd()
    }

    override fun showAd(onAdClosed: () -> Unit) {
        showInterstitialAd(onAdClosed)
    }
}

