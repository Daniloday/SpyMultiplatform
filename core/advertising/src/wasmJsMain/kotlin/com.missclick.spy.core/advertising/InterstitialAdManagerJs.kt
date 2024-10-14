package com.missclick.spy.core.advertising



internal class InterstitialAdManagerJs: InterstitialAdManager {

    init {
        loadAd()
    }

    private fun loadAd() {

    }

    override fun showAd(onAdClosed: () -> Unit) {
        onAdClosed()
    }
}
