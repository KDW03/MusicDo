plugins {
    id("musicdo.android.feature")
}

android.namespace = "com.najudoryeong.musicdo.feature.player"

dependencies {
    implementation(project(":core:media-service"))

    implementation(libs.coil.kt)
    implementation(libs.renderscript.intrinsics.replacement.toolkit)
}
