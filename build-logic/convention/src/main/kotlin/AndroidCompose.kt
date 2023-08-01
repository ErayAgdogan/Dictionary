import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.api.Project

public fun Project.applyCompose(commonExtension: CommonExtension<*, *, *, *>) {


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
            val bom = libs.findLibrary("androidx-compose-bom").get()
            add("implementation", platform(bom))
            add("androidTestImplementation", platform(bom))
            "implementation"(libs.findLibrary("compose.ui").get())
            "implementation"(libs.findLibrary("compose.ui.tooling.preview").get())
            "implementation"(libs.findLibrary("compose.material3").get())
            "implementation"(libs.findLibrary("compose.material3.window.size").get())
            "implementation"(libs.findLibrary("compose.constraintlayout").get())
            "implementation"(libs.findLibrary("compose.navigation").get())

            "implementation"(libs.findLibrary("lifecycle.runtime.compose").get())

            "implementation"(libs.findLibrary("paging.runtime").get())
            "implementation"(libs.findLibrary("paging.compose").get())

            "implementation"(libs.findLibrary("viewmodel.compose").get())
            "implementation"(libs.findLibrary("viewmodel.savedstate").get())
            "implementation"(libs.findLibrary("hilt.navigation.compose").get())

            "androidTestImplementation"(libs.findLibrary("compose.ui.test.junit").get())

            "debugImplementation"(libs.findLibrary("compose.ui.tooling").get())
            "debugImplementation"(libs.findLibrary("compose.ui.test.manifest").get())
        }
    }

}