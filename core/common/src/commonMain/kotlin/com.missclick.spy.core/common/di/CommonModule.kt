package com.missclick.spy.core.common.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import org.koin.core.qualifier.named
import org.koin.dsl.module

val commonModule = module {
    single(named(SpyDispatchers.IO)) { Dispatchers.IO }
    single(named(SpyDispatchers.Main)) { Dispatchers.Main }
    single(named(SpyDispatchers.Default)) { Dispatchers.Default }
    single(named(SpyDispatchers.Unconfined)) { Dispatchers.Unconfined }
    single<CoroutineScope> {
        provideApplicationScope(get(named(SpyDispatchers.Default)))
    }
}

private fun provideApplicationScope(dispatcher: CoroutineDispatcher): CoroutineScope =
    CoroutineScope(SupervisorJob() + dispatcher)

enum class SpyDispatchers {
    IO, Main, Default, Unconfined
}