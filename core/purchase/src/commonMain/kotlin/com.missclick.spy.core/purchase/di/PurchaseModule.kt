package com.missclick.spy.core.purchase.di

import org.koin.core.module.Module
import org.koin.dsl.module


internal expect fun platformModule(): Module

val purchaseModule = module {
    includes(platformModule())
}