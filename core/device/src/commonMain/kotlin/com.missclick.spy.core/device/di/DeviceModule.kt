package com.missclick.spy.core.device.di

import com.missclick.spy.core.device.DeviceDataSource
import com.missclick.spy.core.device.DeviceDataSourceImpl
import org.koin.dsl.module

val deviceModule = module {
    single<DeviceDataSource> { DeviceDataSourceImpl() }
}