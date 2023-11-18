plugins {
    id("musicdo.android.feature")
}

android.namespace = "com.najudoryeong.musicdo.feature.home"

dependencies {
    implementation(project(":core:media-service"))
}
