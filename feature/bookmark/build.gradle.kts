plugins {
    id("com.goander.dictionary.android.library")
    id("com.goander.dictionary.android.hilt")
    id("com.goander.dictionary.android.library.compose")
}

android {
    namespace = "com.goander.dictionary.bookmark"
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:ui"))
}