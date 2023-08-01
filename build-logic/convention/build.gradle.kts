
plugins {
    `kotlin-dsl`
}

group = "com.goander.dictionary.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("AndroidApplication") {
            id = "com.goander.dictionary.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("AndroidApplicationCompose") {
            id = "com.goander.dictionary.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "com.goander.dictionary.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidHilt") {
            id = "com.goander.dictionary.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "com.goander.dictionary.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
    }
}