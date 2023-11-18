plugins {
    id("musicdo.android.library")
    id("musicdo.android.hilt")
}

android {
    namespace = "com.najudoryeong.musicdo.core.datastore.test"
}

dependencies {
    api(project(":core:datastore"))
    api(libs.androidx.dataStore.core)

    implementation(libs.protobuf.kotlin.lite)
    implementation(project(":core:common"))
    implementation(project(":core:testing"))
}
