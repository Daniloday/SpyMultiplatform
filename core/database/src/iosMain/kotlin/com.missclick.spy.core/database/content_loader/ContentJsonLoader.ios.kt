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

internal class ContentJsonLoaderIos : ContentJsonLoader {

    @OptIn(ExperimentalForeignApi::class)
    override suspend fun loadJson(fileName: String): String =
        withContext(Dispatchers.Default) {

            val name = fileName.substringBeforeLast(".")
            val ext = fileName.substringAfterLast(".", "")

            val path = NSBundle.mainBundle
                .pathForResource(name, ext)
                ?: return@withContext ""

            NSString
                .stringWithContentsOfFile(path, NSUTF8StringEncoding, null)
                ?.toString()
                ?: ""
        }
}