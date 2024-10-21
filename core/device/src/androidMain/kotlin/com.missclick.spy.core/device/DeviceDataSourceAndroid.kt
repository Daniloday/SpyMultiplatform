package com.missclick.spy.core.device

import android.app.Activity
import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import com.google.android.play.core.ktx.requestReview
import com.google.android.play.core.review.ReviewException
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.review.model.ReviewErrorCode
import com.google.android.play.core.review.testing.FakeReviewManager
import java.util.Locale


internal class DeviceDataSourceAndroid(
    private val activity: Lazy<Activity>,
    private val context: Context
) : DeviceDataSource {

    override suspend fun getCurrentLanguageCode(): String {
        return Locale.getDefault().language
    }

    override suspend fun setLanguage(languageCode: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            activity.value.getSystemService(LocaleManager::class.java)
                .applicationLocales = LocaleList.forLanguageTags(languageCode)
        } else {
            val locale = Locale(languageCode)
            Locale.setDefault(locale)
            val configuration = activity.value.resources.configuration
            configuration.setLocale(locale)
            activity.value.resources.updateConfiguration(configuration, activity.value.resources.displayMetrics)
        }
    }

    override suspend fun requestRateUs() {
        val manager = ReviewManagerFactory.create(context)
        val request = manager.requestReviewFlow()
        request.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val reviewInfo = task.result
                manager.launchReviewFlow(activity.value, reviewInfo)
            }
        }

    }

}