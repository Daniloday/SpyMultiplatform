package com.missclick.spy.core.purchase.di


import com.missclick.spy.core.purchase.PurchaseManager
import com.missclick.spy.core.purchase.PurchaseManagerAndroid
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun platformModule(): Module = module {
    single<PurchaseManager> {
        PurchaseManagerAndroid(activity = inject())
    }
}