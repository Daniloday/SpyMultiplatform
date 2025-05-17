plugins {
    alias(libs.plugins.spy.feature)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.purchase)
            implementation(projects.core.domain)
            implementation(projects.core.data)
        }
    }
}
