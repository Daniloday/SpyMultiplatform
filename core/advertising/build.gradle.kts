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
        }
    }
}

android {
    defaultConfig {
        buildConfigField("String", "ADMOB_BANNER_ID", "\"${secretKeyProperties["ADMOB_BANNER_ID"]}\"")
        buildConfigField("String", "ADMOB_REWARDED_INTERSTITIAL_ID", "\"${secretKeyProperties["ADMOB_REWARDED_INTERSTITIAL_ID"]}\"")
    }
    buildFeatures {
        buildConfig = true
    }
}
