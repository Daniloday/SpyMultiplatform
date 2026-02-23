package com.missclick.spy.core.database.content_loader

internal interface ContentJsonLoader {
    suspend fun loadJson(fileName: String): String
}