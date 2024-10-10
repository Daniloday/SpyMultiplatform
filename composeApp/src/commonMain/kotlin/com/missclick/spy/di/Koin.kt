package com.missclick.spy.di

import com.missclick.spy.core.data.di.dataModule
import com.missclick.spy.core.database.di.databaseModule
import com.missclick.spy.core.datastore.di.dataStoreModule
import com.missclick.spy.core.device.di.deviceModule
import com.missclick.spy.core.domain.di.domainModule
import com.missclick.spy.feature.collections.di.collectionsModule
import com.missclick.spy.feature.game.di.gameModule
import com.missclick.spy.feature.game_options.di.gameOptionsModule
import com.missclick.spy.feature.rules.di.rulesModule
import com.missclick.spy.feature.settings.di.settingsModule
import com.missclick.spy.feature.words.di.wordsModule
import org.koin.core.context.startKoin
import org.koin.dsl.module


val appModule = listOf(
    dataModule,
    dataStoreModule,
    databaseModule,
    rulesModule,
    gameOptionsModule,
    deviceModule,
    domainModule,
    gameModule,
    collectionsModule,
    wordsModule,
    settingsModule,
)

