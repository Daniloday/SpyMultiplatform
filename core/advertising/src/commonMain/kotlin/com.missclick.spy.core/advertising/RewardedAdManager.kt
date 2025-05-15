package com.missclick.spy.core.advertising

interface RewardedAdManager {
    fun showAd(onAdSkipped: () -> Unit, onAdWatched: () -> Unit)
}