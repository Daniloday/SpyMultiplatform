plugins {
    alias(libs.plugins.spy.kotlinMultiplatform)
    alias(libs.plugins.spy.serialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.model)
            implementation(projects.core.common)
            implementation(libs.androidx.datastore)
        }
    }
}

