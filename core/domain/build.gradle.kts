plugins {
    id("musicdo.android.library")
    id("musicdo.android.library.jacoco")
    kotlin("kapt")
}

android.namespace = "com.najudoryeong.musicdo.core.domain"

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:model"))
    implementation(libs.hilt.android)

    kapt(libs.hilt.compiler)
    testImplementation(project(":core:testing"))
}
