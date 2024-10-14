package com.missclick.spy.core.device.di

import com.missclick.spy.core.device.DeviceDataSource
import com.missclick.spy.core.device.DeviceDataSourceJs
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun platformModule(): Module = module {
    single<DeviceDataSource> { DeviceDataSourceJs() }
}