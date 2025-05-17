package com.missclick.spy.core.purchase

import android.app.Activity
import android.util.Log
import com.adapty.Adapty
import com.adapty.utils.AdaptyResult

internal class PurchaseManagerAndroid(
    private val activity: Lazy<Activity>,
) : PurchaseManager {
    override fun buy(onResult: (Boolean) -> Unit) {
        try {
            Adapty.getPaywall(
                placementId = "Premium"
            ) { paywall ->
                if (paywall is AdaptyResult.Success) {
                    Adapty.getPaywallProducts(paywall.value) { paywallProducts ->
                        if (paywallProducts is AdaptyResult.Success) {
                            paywallProducts.value.firstOrNull()?.let { paywallProduct ->
                                Adapty.makePurchase(
                                    activity.value,
                                    paywallProduct
                                ) { purchaseResult ->
                                    when (purchaseResult) {
                                        is AdaptyResult.Success -> {
                                            onResult(true)
                                        }

                                        is AdaptyResult.Error -> {
                                            println("buy error")
                                            Log.e(
                                                "Purchase",
                                                "Error: ${purchaseResult.error.message}"
                                            )
                                            onResult(false)
                                        }
                                    }
                                }
                            } ?: onResult(false)
                        } else {
                            onResult(false)
                        }
                    }
                } else {
                    onResult(false)
                }
            }
        } catch (e: Throwable) {
            onResult(false)
        }

    }

    override fun restore(onResult: (Boolean) -> Unit) {
        try {
            Adapty.restorePurchases { result ->
                if (result is AdaptyResult.Success) {
                    val profile = result.value
                    val hasActiveAccess = profile.accessLevels["premium"]?.isActive == true
                    onResult(hasActiveAccess)
                } else {
                    onResult(false)
                }
            }
        } catch (e: Throwable) {
            onResult(false)
        }
    }
}