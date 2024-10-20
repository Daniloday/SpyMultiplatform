plugins {
    `kotlin-dsl`
}

group = "com.missclick.spy.buildlogic"

dependencies {
    compileOnly(libs.plugins.kotlinMultiplatform.toDep())
    compileOnly(libs.plugins.androidApplication.toDep())
    compileOnly(libs.plugins.androidLibrary.toDep())
    compileOnly(libs.plugins.jetbrainsCompose.toDep())
    compileOnly(libs.plugins.compose.compiler.toDep())
    compileOnly(libs.plugins.kotlin.serialization.toDep())
}

fun Provider<PluginDependency>.toDep() = map {
    "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}"
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("kotlinMultiplatform") {
            id = "com.missclick.spy.kotlinMultiplatform"
            implementationClass = "KotlinMultiplatformConventionPlugin"
        }
        register("composeMultiplatform") {
            id = "com.missclick.spy.composeMultiplatform"
            implementationClass = "ComposeMultiplatformConventionPlugin"
        }
        register("FeatureConventionPlugin") {
            id = "com.missclick.spy.feature"
            implementationClass = "FeatureConventionPlugin"
        }
        register("RoomConventionPlugin") {
            id = "com.missclick.spy.room"
            implementationClass = "RoomConventionPlugin"
        }
        register("SerializationConventionPlugin") {
            id = "com.missclick.spy.serialization"
            implementationClass = "SerializationConventionPlugin"
        }
    }
}