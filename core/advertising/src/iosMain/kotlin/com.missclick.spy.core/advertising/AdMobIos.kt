package com.missclick.spy.core.advertising

import platform.UIKit.UIViewController

class AdMobIos(
    val bannerViewController: () -> UIViewController,
    val loadRewardedAd: () -> Unit,
    val showRewardedAd: (onAdClosed: () -> Unit) -> Unit,
)