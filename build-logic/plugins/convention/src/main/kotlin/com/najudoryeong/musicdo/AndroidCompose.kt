package com.najudoryeong.musicdo

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies


/**
 * compose 설정 확장 함수
 * 1. 벡터 드로어블을 지원
 * 2. Compose 활성화 및 컴파일러 버전 설정 및 의존성 설정
 */
internal fun Project.configureAndroidCompose(commonExtension: CommonExtension<*, *, *, *>) =
    with(commonExtension) {
        defaultConfig.vectorDrawables.useSupportLibrary = true
        buildFeatures.compose = true
        composeOptions.kotlinCompilerExtensionVersion =
            libs.versions.androidx.compose.compiler.get()

        dependencies {
            val bom = libs.androidx.compose.bom
            add("implementation", platform(bom))
            add("androidTestImplementation", platform(bom))
        }
    }
