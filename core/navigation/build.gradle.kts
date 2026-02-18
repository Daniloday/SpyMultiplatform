plugins {
    alias(libs.plugins.spy.kotlinMultiplatform)
    alias(libs.plugins.spy.composeMultiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.feature.rules)
            implementation(projects.feature.gameOptions)
            implementation(projects.feature.game)
            implementation(projects.feature.guide)
            implementation(projects.feature.settings)
            implementation(projects.feature.sets)
            implementation(projects.feature.words)
        }
    }
}

