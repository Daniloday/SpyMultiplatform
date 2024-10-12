package com.missclick.spy.core.device.di

import com.missclick.spy.core.device.DeviceDataSource
import org.koin.core.module.Module
import org.koin.dsl.module

internal expect fun platformModule(): Module

val deviceModule = module {
    includes(platformModule())
}