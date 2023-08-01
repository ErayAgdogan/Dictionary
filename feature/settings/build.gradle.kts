plugins {
    id("com.goander.dictionary.android.library")
    id("com.goander.dictionary.android.hilt")
    id("com.goander.dictionary.android.library.compose")
}

android {
    namespace = "com.goander.dictionary.settings"
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:connectivity"))
    implementation(project(":core:design"))
    implementation(project(":core:ui"))
    implementation(project(":core:theme"))
}