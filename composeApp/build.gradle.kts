import com.google.firebase.crashlytics.buildtools.gradle.CrashlyticsExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.spy.composeMultiplatform)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
}

val secretKeyProperties: Properties by lazy {
    val secretKeyPropertiesFile = rootProject.file("secrets.properties")
    Properties().apply { secretKeyPropertiesFile.inputStream().use { secret -> load(secret) } }
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            linkerOpts.add("-lsqlite3")
        }
    }
    
    sourceSets {
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.android)
            implementation(libs.androidx.core.splashscreen)
            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.analytics)
            implementation(libs.firebase.crashlytics)
        }
        commonMain.dependencies {

            implementation(projects.core.ui)
            implementation(projects.core.model)
            implementation(projects.core.navigation)
            implementation(projects.core.domain)
            implementation(projects.core.data)
            implementation(projects.core.database)
            implementation(projects.core.datastore)
            implementation(projects.core.device)
            implementation(projects.core.advertising)
            implementation(projects.core.common)
            implementation(projects.core.purchase)

            implementation(projects.feature.rules)
            implementation(projects.feature.gameOptions)
            implementation(projects.feature.game)
            implementation(projects.feature.guide)
            implementation(projects.feature.settings)
            implementation(projects.feature.sets)
            implementation(projects.feature.words)
            implementation(projects.feature.premium)

        }
    }
}

android {
    namespace = "com.missclick.spy"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    defaultConfig {
        applicationId = "com.missclick.spy"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 19
        versionName = "2.2.0"
        manifestPlaceholders["ADMOB_APPLICATION_ID"] = secretKeyProperties.getProperty("ADMOB_APPLICATION_ID")
        buildConfigField("String", "UNITY_GAME_ID", "\"${secretKeyProperties["UNITY_GAME_ID"]}\"")
        buildConfigField("String", "ADAPTY_API_KEY", "\"${secretKeyProperties["ADAPTY_API_KEY"]}\"")
        androidResources.localeFilters += listOf("en", "ru", "uk")
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("debug")
            configure<CrashlyticsExtension> {
                mappingFileUploadEnabled = true
            }
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }

    dependencies {
        debugImplementation(compose.uiTooling)
    }

}

