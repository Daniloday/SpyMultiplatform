package com.missclick.spy.core.data.di

import com.missclick.spy.core.data.DeviceRepo
import com.missclick.spy.core.data.OptionsRepo
import com.missclick.spy.core.data.WordRepo
import com.missclick.spy.core.data.repo.DeviceRepoImpl
import com.missclick.spy.core.data.repo.OptionsRepoImpl
import com.missclick.spy.core.data.repo.WordRepoImpl
import org.koin.dsl.module

val dataModule = module {
    single<OptionsRepo> { OptionsRepoImpl(get()) }
    single<WordRepo> { WordRepoImpl(get()) }
    single<DeviceRepo> { DeviceRepoImpl(get()) }
}