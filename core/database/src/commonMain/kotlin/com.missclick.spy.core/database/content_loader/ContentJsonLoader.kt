package com.missclick.spy.core.database.content_loader

internal interface ContentJsonLoader {
    suspend fun loadSpyContentJson(): String
}