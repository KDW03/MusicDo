plugins {
    id("musicdo.android.library")
}

android.namespace = "com.najudoryeong.musicdo.core.model"

dependencies {
    implementation(project(":core:media-common"))
    api(libs.kotlinx.datetime)
}