import androidx.room.gradle.RoomExtension
import com.missclick.spy.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class RoomConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager){
            apply(libs.findPlugin("room").get().get().pluginId)
            apply(libs.findPlugin("ksp").get().get().pluginId)
        }

        extensions.configure<RoomExtension> {
            schemaDirectory("$projectDir/schemas")
        }

        extensions.configure<KotlinMultiplatformExtension> {
            sourceSets.apply {
                commonMain.dependencies {
                    implementation(libs.findLibrary("androidx.room.runtime").get())
                }
                iosMain.dependencies {
                    implementation(libs.findLibrary("sqlite.bundled").get())
                }
            }
        }

        dependencies {
            add("ksp", libs.findLibrary("androidx.room.compiler").get())
        }
    }
}