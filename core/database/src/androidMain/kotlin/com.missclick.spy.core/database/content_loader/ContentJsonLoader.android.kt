package com.missclick.spy.core.database.content_loader

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader

internal class ContentJsonLoaderAndroid(
    private val context: Context,
): ContentJsonLoader {
    override suspend fun loadSpyContentJson(): String = withContext(Dispatchers.IO) {
        context.assets.open("spy-content.json").use { input ->
            input.bufferedReader(Charsets.UTF_8).use(BufferedReader::readText)
        }
    }
}