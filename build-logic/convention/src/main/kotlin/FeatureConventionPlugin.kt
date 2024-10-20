import com.android.build.api.dsl.LibraryExtension
import com.missclick.spy.buildlogic.configureKotlinAndroid
import com.missclick.spy.buildlogic.configureKotlinMultiplatform
import com.missclick.spy.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class FeatureConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager){
            apply("com.missclick.spy.kotlinMultiplatform")
            apply("com.missclick.spy.composeMultiplatform")
        }

        extensions.configure<KotlinMultiplatformExtension> {
            sourceSets.apply {
                commonMain.dependencies {
                    implementation(project(":core:ui"))
                }
            }
        }

    }
}