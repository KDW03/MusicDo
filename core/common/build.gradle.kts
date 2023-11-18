plugins {
    id("musicdo.android.library")
    id("musicdo.android.library.jacoco")
    id("musicdo.android.hilt")
}

android {
    namespace = "com.najudoryeong.musicdo.core.common"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
    testImplementation(project(":core:testing"))
}