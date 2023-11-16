package com

import com.android.build.gradle.LibraryExtension
import com.najudoryeong.musicdo.configureAndroidCompose
import com.najudoryeong.musicdo.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType


/**
 * 플러그인 for 라이브러리 모듈 컴포즈
 */
class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply(libs.plugins.android.library.get().pluginId)
        val extension = extensions.getByType<LibraryExtension>()
        configureAndroidCompose(extension)
    }
}
