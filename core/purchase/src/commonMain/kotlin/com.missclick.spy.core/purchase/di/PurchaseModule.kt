package com.missclick.spy.core.purchase.di

import com.missclick.spy.core.purchase.PurchaseManager
import com.missclick.spy.core.purchase.RevenueCatPurchaseManager
import org.koin.core.module.Module
import org.koin.dsl.module


internal expect fun platformModule(): Module

val purchaseModule = module {
    includes(platformModule())
    single <PurchaseManager> { RevenueCatPurchaseManager() }
}