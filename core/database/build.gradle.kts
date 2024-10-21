plugins {
    alias(libs.plugins.spy.kotlinMultiplatform)
    alias(libs.plugins.spy.room)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.model)
        }
    }
}
