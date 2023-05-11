
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidViewModelComposeConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")
            val commonExtension = extensions.getByType<LibraryExtension>()
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            commonExtension.apply {
                buildFeatures {
                    compose = true
                }

                composeOptions {
                    kotlinCompilerExtensionVersion =
                        libs.findVersion("composeCompiler").get().toString()
                }

                dependencies {
                    "implementation"(libs.findLibrary("viewmodel.compose").get())
                    "implementation"(libs.findLibrary("viewmodel.savedstate").get())
                    "implementation"(libs.findLibrary("hilt.navigation.compose").get())

                }
            }
        }
    }

}