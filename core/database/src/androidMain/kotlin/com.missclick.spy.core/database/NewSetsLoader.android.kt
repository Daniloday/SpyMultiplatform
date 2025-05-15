package com.missclick.spy.core.database

import android.content.Context
import com.missclick.spy.core.database.migration.NewSetsLoader
import java.io.InputStream

class NewSetsLoaderAndroid(
    private val context: Context
): NewSetsLoader() {

    override fun load(fileName: String): String {
        val file: InputStream = context.assets.open(fileName)
        val size = file.available()
        val buffer = ByteArray(size)
        file.read(buffer)
        file.close()
        return String(buffer, charset("UTF-8"))
    }
}