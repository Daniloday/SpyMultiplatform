plugins {
    alias(libs.plugins.spy.kotlinMultiplatform)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.model)
            implementation(libs.androidx.room.runtime)
        }
        iosMain.dependencies {
            implementation(libs.sqlite.bundled)
        }
    }
}

room {
    schemaDirectory ("$projectDir/schemas")
}

dependencies {
    ksp(libs.androidx.room.compiler)
}
