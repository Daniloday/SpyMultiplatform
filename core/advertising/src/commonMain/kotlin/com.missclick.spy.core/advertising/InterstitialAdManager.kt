package com.missclick.spy.core.advertising

interface InterstitialAdManager {
    fun showAd(onAdClosed: () -> Unit)
}