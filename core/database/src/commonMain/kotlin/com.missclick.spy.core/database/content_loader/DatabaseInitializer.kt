package com.missclick.spy.core.database.content_loader

import kotlinx.coroutines.launch

internal class DatabaseInitializer(
    private val seeder: ContentSeeder,
    private val scope: kotlinx.coroutines.CoroutineScope,
) {
    fun init() {
        scope.launch {
            seeder.syncIfNeeded()
        }
    }
}