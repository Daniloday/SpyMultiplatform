package com.missclick.spy.core.domain.di

import com.missclick.spy.core.domain.GetOptionsUseCase
import com.missclick.spy.core.domain.SetActualLanguageUseCase
import com.missclick.spy.core.domain.SetLanguageUseCase
import org.koin.dsl.module

val domainModule = module {
    single { GetOptionsUseCase(get(), get(), get()) }
    single { SetLanguageUseCase(get(), get()) }
    single { SetActualLanguageUseCase(get(), get()) }
}