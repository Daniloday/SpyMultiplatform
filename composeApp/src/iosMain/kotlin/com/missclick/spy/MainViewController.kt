package com.missclick.spy

import androidx.compose.ui.window.ComposeUIViewController
import platform.Foundation.NSUserDefaults
import platform.Foundation.setValue

fun MainViewController() = ComposeUIViewController {
    App(onChangeLanguage = ::setLanguage)
}

private fun setLanguage(languageCode: String) {
    println("set language $languageCode")
    val defaults = NSUserDefaults.standardUserDefaults
    defaults.setValue(
        value = languageCode,
        forKey = "AppleLanguages"
    )
    defaults.synchronize()
    println("here lang")
}