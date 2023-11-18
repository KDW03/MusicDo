plugins {
    id("musicdo.android.library")
    id("musicdo.android.library.compose")
}

android.namespace = "com.najudoryeong.musicdo.core.permission"

dependencies {
    implementation(project(":core:designsystem"))
    implementation(libs.androidx.core.ktx)
    api(libs.accompanist.permissions)
}
