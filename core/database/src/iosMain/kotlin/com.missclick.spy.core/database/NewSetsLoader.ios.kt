package com.missclick.spy.core.database

import com.missclick.spy.core.database.migration.NewSetsLoader
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSBundle
import platform.Foundation.NSFileManager
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.stringWithContentsOfFile

class NewSetsLoaderIos : NewSetsLoader() {

    @OptIn(ExperimentalForeignApi::class)
    override fun load(fileName: String): String {
        val (name, ext) = splitNameExt(fileName)
        debugPrintBundle()
        val path = NSBundle.mainBundle.pathForResource(
            name = name,
            ofType = ext
        ) ?: error("Resource not found in iOS bundle: '$fileName'")

        return NSString.stringWithContentsOfFile(
            path,
            encoding = NSUTF8StringEncoding,
            error = null
        )?.toString()
            ?: error("Failed to read resource as UTF-8: '$fileName'")
    }

    private fun splitNameExt(fileName: String): Pair<String, String?> {
        val lastDot = fileName.lastIndexOf('.')
        if (lastDot <= 0 || lastDot == fileName.lastIndex) return fileName to null
        return fileName.substring(0, lastDot) to fileName.substring(lastDot + 1)
    }

    fun debugPrintBundle() {
        val bundle = NSBundle.mainBundle
        val bundlePath = bundle.bundlePath

        println("=== MAIN BUNDLE PATH ===")
        println(bundlePath)

        val fm = NSFileManager.defaultManager
        val items = fm.subpathsAtPath(bundlePath) ?: emptyList<Any>()

        println("=== BUNDLE CONTENT ===")
        for (item in items) {
            println(item.toString())
        }
        println("=== END ===")
    }
}
