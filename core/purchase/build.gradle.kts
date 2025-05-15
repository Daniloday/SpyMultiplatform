plugins {
    alias(libs.plugins.spy.kotlinMultiplatform)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            api(project.dependencies.platform(libs.adapty.bom))
            api(libs.adapty)
            api(libs.adapty.ui)
        }
    }
}
