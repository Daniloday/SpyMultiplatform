plugins {
    alias(libs.plugins.spy.feature)
}


kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.domain)
            implementation(projects.core.data)
            implementation(projects.core.advertising)
            implementation(projects.core.common)

            implementation(libs.kotlinx.datetime)
        }
    }
}
