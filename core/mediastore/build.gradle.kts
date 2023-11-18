plugins {
    id("musicdo.android.library")
    id("musicdo.android.hilt")
}

android.namespace = "com.najudoryeong.musicdo.core.mediastore"

dependencies {
    implementation(project(":core:model"))
}
