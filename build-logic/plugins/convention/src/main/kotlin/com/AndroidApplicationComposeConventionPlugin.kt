package com

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.najudoryeong.musicdo.configureAndroidCompose
import com.najudoryeong.musicdo.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

/**
 * 플러그인 for 애플리케이션 모듈 컴포즈
 */
class AndroidApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply(libs.plugins.android.application.get().pluginId)
        val extension = extensions.getByType<BaseAppModuleExtension>()
        configureAndroidCompose(extension)
    }
}
