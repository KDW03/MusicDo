package com.najudoryeong.musicdo

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions


/**
 * Kotlin 및 Android 관련 설정
 * 1. 컴파일 및 최소 SDK 설정
 * 2. Java Version 설정
 */
internal fun Project.configureKotlinAndroid(commonExtension: CommonExtension<*, *, *, *>) =
    with(commonExtension) {
        compileSdk = libs.versions.android.compileSdk.get().toInt()

        defaultConfig {
            minSdk = libs.versions.android.minSdk.get().toInt()
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
            isCoreLibraryDesugaringEnabled = true
        }

        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_17.toString()
        }

        dependencies.add("coreLibraryDesugaring", libs.desugar.jdk.libs)
    }

/**
 * Kotlin JVM 옵션을 구성할 수 있는 블록을 제공
 */
private fun CommonExtension<*, *, *, *>.kotlinOptions(block: KotlinJvmOptions.() -> Unit) =
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)
