plugins {
    alias(libs.plugins.spy.kotlinMultiplatform)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            api(libs.purchases)
            implementation(libs.google.playReview)
        }
    }
}
