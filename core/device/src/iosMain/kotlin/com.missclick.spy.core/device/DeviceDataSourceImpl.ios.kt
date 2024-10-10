package com.missclick.spy.core.device

import platform.Foundation.NSLocale
import platform.Foundation.currentLocale
import platform.Foundation.languageCode



internal actual val languageCode = NSLocale.currentLocale.languageCode