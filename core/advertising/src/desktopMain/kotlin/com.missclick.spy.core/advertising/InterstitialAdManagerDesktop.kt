package com.missclick.spy.core.advertising



internal class InterstitialAdManagerDesktop: InterstitialAdManager {

    init {
        loadAd()
    }

    private fun loadAd() {

    }

    override fun showAd(onAdClosed: () -> Unit) {
        onAdClosed()
    }
}
