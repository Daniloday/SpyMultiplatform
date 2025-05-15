package com.missclick.spy.core.purchase

import android.app.Activity
import android.util.Log
import com.adapty.Adapty
import com.adapty.models.AdaptyPaywall
import com.adapty.models.AdaptyPaywallProduct
import com.adapty.models.AdaptyPurchaseResult
import com.adapty.ui.AdaptyUI
import com.adapty.ui.listeners.AdaptyUiObserverModeHandler
import com.adapty.utils.AdaptyResult

internal class PurchaseManagerAndroid(
    private val activity: Lazy<Activity>,
) : PurchaseManager {
    override fun buy() {
        Adapty.getPaywall(
            placementId = ""
        ) { paywall ->
            if (paywall is AdaptyResult.Success) {
                Adapty.getPaywallProducts(paywall.value) { paywallProducts ->
                    if (paywallProducts is AdaptyResult.Success) {
                        paywallProducts.value.firstOrNull()?.let { paywallProduct ->
                            Adapty.makePurchase(activity.value, paywallProduct) { purchaseResult ->
                                when (purchaseResult) {
                                    is AdaptyResult.Success -> {
                                        // Успешно куплено, активируй премиум
                                    }

                                    is AdaptyResult.Error -> {
                                        // Покупка не удалась — покажи сообщение
                                        Log.e("Purchase", "Error: ${purchaseResult.error.message}")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun restore() {
        TODO("Not yet implemented")
    }
}