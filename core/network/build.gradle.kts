plugins {
    id("com.goander.dictionary.android.library")
    id("com.goander.dictionary.android.hilt")
}

android {
    namespace = "com.goander.dictionary.network"
}

dependencies {
    implementation(libs.retrofit2)
    implementation(libs.retrofit2.gson.converter)
}

kapt {
    correctErrorTypes = true
}