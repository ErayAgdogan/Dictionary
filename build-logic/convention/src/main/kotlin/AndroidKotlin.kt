import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

public fun Project.applyKotlin(commonExtension: CommonExtension<*, *, *, *>) {
    commonExtension.apply{
        compileSdk = 33
        defaultConfig {
            minSdk = 23
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11

        }
        // Set JVM target to 11
        (this as ExtensionAware).extensions.configure<KotlinJvmOptions>("kotlinOptions") {
            jvmTarget = JavaVersion.VERSION_11.toString()
        }
    }
}