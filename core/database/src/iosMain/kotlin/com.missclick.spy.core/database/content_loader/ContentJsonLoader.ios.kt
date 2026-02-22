package com.missclick.spy.core.database.content_loader

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import platform.Foundation.NSBundle
import platform.Foundation.NSData
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.dataWithContentsOfFile
import platform.Foundation.stringWithContentsOfFile

internal  class ContentJsonLoaderIos: ContentJsonLoader {

     @OptIn(ExperimentalForeignApi::class)
     override suspend fun loadSpyContentJson(): String = withContext(Dispatchers.Default) {
        val path = NSBundle.mainBundle.pathForResource("spy-content", "json")
            ?: error("spy-content.json not found in iOS bundle")

        // Fast and simple
        NSString.stringWithContentsOfFile(path, NSUTF8StringEncoding, null) as String
    }
}