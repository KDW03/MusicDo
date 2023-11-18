plugins {
    id("musicdo.android.feature")
}

android.namespace = "com.najudoryeong.musicdo.feature.search"

dependencies {
    implementation(project(":core:media-service"))
}
