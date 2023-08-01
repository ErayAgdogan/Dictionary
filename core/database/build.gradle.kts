plugins {
    id("com.goander.dictionary.android.library")
    id("com.goander.dictionary.android.hilt")
}

android {
    namespace = "com.goander.dictionary.database"
}

dependencies {

    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
    // To use Kotlin annotation processing tool (kapt)
    kapt(libs.room.compiler)
    // optional - Kotlin Extensions and Coroutines support for Room
    api(libs.room.ktx)
    // optional - Test helpers
    testImplementation(libs.room.testing)
    // optional - Paging 3 Integration
    api(libs.room.paging)

}

kapt {
    correctErrorTypes = true
}