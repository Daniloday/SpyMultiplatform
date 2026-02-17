package com.missclick.spy.core.advertising

import android.app.Activity
import com.google.android.play.core.review.ReviewManagerFactory

class RateUsAndroid(val activity: Activity): RateUs  {
    override fun show() {
        val manager = ReviewManagerFactory.create(activity)
        val request = manager.requestReviewFlow()
        request.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val reviewInfo = task.result
                manager.launchReviewFlow(activity, reviewInfo)
            }
        }
    }
}