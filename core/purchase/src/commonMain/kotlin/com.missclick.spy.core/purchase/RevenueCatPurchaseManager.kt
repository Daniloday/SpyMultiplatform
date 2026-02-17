package com.missclick.spy.core.purchase

import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.PurchasesDelegate
import com.revenuecat.purchases.kmp.models.CustomerInfo
import com.revenuecat.purchases.kmp.models.PurchasesError
import com.revenuecat.purchases.kmp.models.StoreProduct
import com.revenuecat.purchases.kmp.models.StoreTransaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RevenueCatPurchaseManager() : PurchaseManager {

    private val _isPremium = MutableStateFlow(false)
    override val isPremium: StateFlow<Boolean> = _isPremium.asStateFlow()

    private val delegate = object : PurchasesDelegate {
        override fun onCustomerInfoUpdated(customerInfo: CustomerInfo) {
            _isPremium.value = customerInfo.entitlements["SpyPremium"]?.isActive == true
        }

        override fun onPurchasePromoProduct(
            product: StoreProduct,
            startPurchase: (
                onError: (error: PurchasesError, userCancelled: Boolean) -> Unit,
                onSuccess: (storeTransaction: StoreTransaction, customerInfo: CustomerInfo) -> Unit
            ) -> Unit
        ) = Unit
    }

    init {
        Purchases.sharedInstance.delegate = delegate

        Purchases.sharedInstance.getCustomerInfo(
            onError = { _isPremium.value = false },
            onSuccess = { customerInfo ->
                _isPremium.value = customerInfo.entitlements["SpyPremium"]?.isActive == true
            }
        )
    }
}
