plugins {
    alias(libs.plugins.spy.kotlinMultiplatform)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.google.playReview)
        }
    }
}