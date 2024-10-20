plugins {
    alias(libs.plugins.spy.kotlinMultiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.model)
            implementation(projects.core.data)
            implementation(projects.core.common)
        }
    }
}