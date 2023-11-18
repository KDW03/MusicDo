plugins {
    id("musicdo.android.library")
    id("musicdo.android.hilt")
}

android.namespace = "com.najudoryeong.musicdo.core.media.notification"

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:media-common"))
    implementation(project(":core:designsystem"))

    implementation(libs.androidx.media3.session)
    implementation(libs.coil.kt)

}
