package com.missclick.spy.core.purchase

interface PurchaseManager {
    fun buy(onResult: (Boolean) -> Unit)
    fun restore(onResult: (Boolean) -> Unit)
}