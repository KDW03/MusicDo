plugins {
    id("musicdo.android.feature")
}

android.namespace = "com.najudoryeong.musicdo.feature.library"

dependencies {
    implementation(project(":core:media-service"))
}