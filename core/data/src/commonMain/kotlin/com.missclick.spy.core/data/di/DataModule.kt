package com.missclick.spy.core.data.di

import com.missclick.spy.core.common.di.SpyDispatchers
import com.missclick.spy.core.data.LanguageRepo
import com.missclick.spy.core.data.OptionsRepo
import com.missclick.spy.core.data.SetRepo
import com.missclick.spy.core.data.WordRepo
import com.missclick.spy.core.data.repo.LanguageRepoImpl
import com.missclick.spy.core.data.repo.OptionsRepoImpl
import com.missclick.spy.core.data.repo.SetRepoImpl
import com.missclick.spy.core.data.repo.WordRepoImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    single<OptionsRepo> {
        OptionsRepoImpl(
            optionsDataSource = get(),
            ioDispatcher = get(named(SpyDispatchers.IO))
        )
    }
    single<WordRepo> {
        WordRepoImpl(
            wordDataSource = get(),
            ioDispatcher = get(named(SpyDispatchers.IO))
        )
    }
    single<SetRepo> {
        SetRepoImpl(
            setDataSource = get(),
            ioDispatcher = get(named(SpyDispatchers.IO))
        )
    }
    single<LanguageRepo> {
        LanguageRepoImpl(
            get(),
            get(),
            ioDispatcher = get(named(SpyDispatchers.IO))
        )
    }
}