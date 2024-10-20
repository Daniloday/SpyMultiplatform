plugins {
    alias(libs.plugins.spy.kotlinMultiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.model)
            implementation(projects.core.datastore)
            implementation(projects.core.database)
            implementation(projects.core.common)
            implementation(projects.core.device)
        }
    }
}
