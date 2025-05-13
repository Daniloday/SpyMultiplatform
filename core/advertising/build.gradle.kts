import java.util.Properties

plugins {
    alias(libs.plugins.spy.kotlinMultiplatform)
    alias(libs.plugins.spy.composeMultiplatform)
}

val secretKeyProperties by lazy {
    val secretKeyPropertiesFile = rootProject.file("secrets.properties")
    Properties().apply { secretKeyPropertiesFile.inputStream().use { secret -> load(secret) } }
}

kotlin {

    sourceSets {
        androidMain.dependencies {
            api(libs.play.services.ads)
            api(libs.unity.ads)
            api(libs.admob.unity)
            api(libs.admob.applovin)
            api(libs.admob.ironsource)
//            api(libs.admob.meta)
        }
    }
}

android {
    defaultConfig {
        buildConfigField("String", "ADMOB_BANNER_ID", "\"${secretKeyProperties["ADMOB_BANNER_ID"]}\"")
        buildConfigField("String", "ADMOB_INTERSTITIAL_ID", "\"${secretKeyProperties["ADMOB_INTERSTITIAL_ID"]}\"")
    }
    buildFeatures {
        buildConfig = true
    }
}
