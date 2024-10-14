package com.missclick.spy

import android.app.Activity
import android.app.LocaleManager
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.missclick.spy.core.domain.SetActualLanguageUseCase
import com.missclick.spy.di.appModule
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import java.util.Locale

class MainActivity : ComponentActivity() {

    val setActualLanguageUseCase by inject<SetActualLanguageUseCase>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(
                appModule,
                module {
                    single<Activity> { this@MainActivity }
                }
            )
        }
        runBlocking {
            setActualLanguageUseCase()
        }
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(Color.TRANSPARENT)
        )
        setContent {
            App()
        }
    }

}