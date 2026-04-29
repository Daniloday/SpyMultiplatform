package com.missclick.spy.core.advertising

import platform.UIKit.UIViewController

class AppLovinIos(
    val bannerViewController: () -> UIViewController,
    val loadInterstitialAd: () -> Unit,
    val showInterstitialAd: (onAdClosed: () -> Unit) -> Unit,
    val loadRewardedAd: () -> Unit,
    val showRewardedAd: (onAdClosed: () -> Unit, onReward: () -> Unit) -> Unit,
)
