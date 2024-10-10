package com.missclick.spy.core.domain.di

import com.missclick.spy.core.domain.GetOptionsUseCase
import org.koin.dsl.module

val domainModule = module {
    single { GetOptionsUseCase(get(), get(), get()) }
    single { GetOptionsUseCase(get(), get(), get()) }
}