plugins {
    id("musicdo.android.library")
    id("musicdo.android.library.jacoco")
    id("musicdo.android.hilt")
    id("musicdo.android.room")
}

android {
    defaultConfig {
        testInstrumentationRunner =
            "com.najudoryeong.musicdo.core.testing.DoTestRunner"
    }
    namespace = "com.najudoryeong.musicdo.core.database"
}

dependencies {
    implementation(project(":core:model"))

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)

    androidTestImplementation(project(":core:testing"))
}
