plugins {
    alias(libs.plugins.spy.kotlinMultiplatform)
    alias(libs.plugins.spy.room)
    alias(libs.plugins.spy.serialization)
}

kotlin {
    android {
        sourceSets["main"].assets.srcDirs("src/commonMain/resources")
    }
    sourceSets {
        val commonMain by getting {
            resources.srcDir("src/commonMain/resources")
        }
        commonMain.dependencies {
            implementation(projects.core.model)
        }
    }
}
