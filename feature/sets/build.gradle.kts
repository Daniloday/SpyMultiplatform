plugins {
    alias(libs.plugins.spy.feature)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.common)
            implementation(projects.core.data)
            implementation(projects.core.domain)
        }
    }
}