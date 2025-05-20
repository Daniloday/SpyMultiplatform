plugins {
    alias(libs.plugins.spy.kotlinMultiplatform)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            api(libs.purchases)
        }
    }
}
