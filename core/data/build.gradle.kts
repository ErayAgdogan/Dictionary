plugins {
    id("com.goander.dictionary.android.library")
    id("com.goander.dictionary.android.hilt")
}

android {
    namespace = "com.goander.dictionary.repository"
}

dependencies {

    implementation(project(":core:network"))
    implementation(project(":core:database"))
    implementation(project(":core:datastore"))
    api(project(":core:model"))
    implementation(libs.paging.runtime)
}

kapt {
    correctErrorTypes = true
}