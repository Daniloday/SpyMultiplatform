package com.missclick.spy.core.device.di

import com.missclick.spy.core.device.DeviceDataSource
import com.missclick.spy.core.device.DeviceDataSourceAndroid
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.inject

internal actual fun platformModule(): Module = module {
    single<DeviceDataSource> {
        DeviceDataSourceAndroid(
            activity = inject(),
            context = get()
        )
    }
}