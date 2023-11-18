plugins {
    id("musicdo.android.library")
    id("musicdo.android.library.compose")
}

android.namespace = "com.najudoryeong.musicdo.core.ui"

dependencies {
    implementation(project(":core:media-common"))
    api(project(":core:designsystem"))
    api(project(":core:model"))
    api(project(":core:domain"))
    implementation(libs.androidx.core.ktx)
}
