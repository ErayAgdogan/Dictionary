
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
        register("androidHilt") {
            id = "com.goander.dictionary.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }

        register("androidCompose") {
            id = "com.goander.dictionary.android.compose"
            implementationClass = "AndroidComposeConventionPlugin"
        }
        register("androidViewModelCompose") {
            id = "com.goander.dictionary.android.viewmodel.compose"
            implementationClass = "AndroidViewModelComposeConventionPlugin"
        }
    }
}