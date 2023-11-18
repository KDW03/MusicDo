plugins {
    id("musicdo.android.library")
    id("musicdo.android.hilt")
}

android.namespace = "com.najudoryeong.musicdo.core.media.service"

dependencies {
    api(project(":core:media-common"))
    implementation(project(":core:media-notification"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:model"))
    implementation(project(":core:domain"))

    implementation(libs.bundles.androidx.media3)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.guava)
}