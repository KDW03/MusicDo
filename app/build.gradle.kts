/*
 * Copyright 2023 KDW03
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import com.najudoryeong.musicdo.DoBuildType

plugins {
    id("musicdo.android.application")
    id("musicdo.android.application.compose")
    id("musicdo.android.application.flavors")
    id("musicdo.android.application.jacoco")
    id("musicdo.android.hilt")
    id("jacoco")
}

android {
    namespace = "com.najudoryeong.musicdo"

    defaultConfig {
        applicationId = "com.najudoryeong.musicdo"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.najudoryeong.musicdo.core.testing.DoTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = DoBuildType.DEBUG.applicationIdSuffix
            manifestPlaceholders["appName"] = "musicdo (Debug)"
        }
        val release by getting {
            isMinifyEnabled = true
            applicationIdSuffix = DoBuildType.RELEASE.applicationIdSuffix
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
            manifestPlaceholders["appName"] = "musicdo"
        }
        create("benchmark") {
            initWith(release)
            matchingFallbacks.add("release")
            signingConfig = signingConfigs.getByName("debug")
            proguardFiles("benchmark-rules.pro")
            isMinifyEnabled = true
            applicationIdSuffix = DoBuildType.BENCHMARK.applicationIdSuffix
            manifestPlaceholders["appName"] = "musicdo (Benchmark)"
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }


}
dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:data"))
    implementation(project(":core:datastore"))
    implementation(project(":core:mediastore"))
    implementation(project(":core:domain"))
    implementation(project(":core:ui"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:model"))
    implementation(project(":core:media-common"))
    implementation(project(":core:media-service"))
    implementation(project(":core:media-notification"))
    implementation(project(":core:permission"))

    implementation(project(":features:home"))
    implementation(project(":features:search"))
    implementation(project(":features:favorite"))
    implementation(project(":features:settings"))
    implementation(project(":features:player"))
    implementation(project(":features:library"))
    implementation(project(":features:playlist"))

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.profileinstaller)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.androidx.compose.material)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
}