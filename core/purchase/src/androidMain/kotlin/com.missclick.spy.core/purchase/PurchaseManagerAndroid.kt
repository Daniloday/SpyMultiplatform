package com.missclick.spy.core.purchase

import android.app.Activity
import com.revenuecat.purchases.PurchaseParams
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.getOfferingsWith
import com.revenuecat.purchases.purchaseWith
import com.revenuecat.purchases.restorePurchasesWith

internal class PurchaseManagerAndroid(
    private val activity: Lazy<Activity>,
) : PurchaseManager {
    override fun buy(onResult: (Boolean) -> Unit) {
        Purchases.sharedInstance.getOfferingsWith(
            onError = { error ->
                onResult(false)
            },
            onSuccess = { offerings ->
                val aPackage = offerings.current?.availablePackages?.firstOrNull()
                if (aPackage != null) {
                    Purchases.sharedInstance.purchaseWith(
                        PurchaseParams.Builder(activity.value, aPackage).build(),
                        onError = { error, userCancelled ->
                            onResult(false)
                        },
                        onSuccess = { storeTransaction, customerInfo ->
                            onResult(true)
                        }
                    )
                } else {
                    onResult(false)
                }
            })

    }

    override fun restore(onResult: (Boolean) -> Unit) {
        Purchases.sharedInstance.restorePurchasesWith(
            onError = {
                onResult(false)
            }, onSuccess = {
                if (it.nonSubscriptionTransactions.isNotEmpty()) {
                    onResult(true)
                } else {
                    onResult(false)
                }
            })
    }
}