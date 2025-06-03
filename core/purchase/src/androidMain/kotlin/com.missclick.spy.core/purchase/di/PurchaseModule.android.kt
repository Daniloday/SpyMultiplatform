package com.missclick.spy.core.purchase.di


import android.app.Activity
import com.missclick.spy.core.purchase.PurchaseManager
import com.missclick.spy.core.purchase.PurchaseManagerAndroid
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.inject

internal actual fun platformModule(): Module = module {
    factory<PurchaseManager> { (activity: Activity) ->
        PurchaseManagerAndroid(activity = activity)
    }
}