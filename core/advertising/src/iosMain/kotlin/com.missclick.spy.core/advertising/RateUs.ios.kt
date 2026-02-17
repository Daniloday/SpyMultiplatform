package com.missclick.spy.core.advertising

import platform.StoreKit.SKStoreReviewController
import platform.UIKit.UIApplication
import platform.UIKit.UIWindowScene


class RateUsIos(): RateUs  {
    override fun show() {
        val scene = UIApplication.sharedApplication
            .connectedScenes
            .firstOrNull() as? UIWindowScene

        if (scene != null) {
            SKStoreReviewController.requestReviewInScene(scene)
        }
    }
}