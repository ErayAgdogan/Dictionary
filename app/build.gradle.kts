plugins {
    id("com.goander.dictionary.android.application")
    id("com.goander.dictionary.android.hilt")
    id("com.goander.dictionary.android.application.compose")

}

android {
    namespace = "com.goander.dictionary"

    defaultConfig {
        applicationId = "com.goander.dictionary"
        versionCode = 4
        versionName = "0.0.5"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(":core:model"))
    implementation(project(":core:data"))
    implementation(project(":core:connectivity"))
    implementation(project(":core:theme"))
    implementation(project(":core:design"))
    implementation(project(":feature:search"))
    implementation(project(":feature:settings"))
    implementation(project(":feature:bookmark"))

    implementation(libs.compose.constraintlayout)

}


kapt {
    correctErrorTypes = true
}