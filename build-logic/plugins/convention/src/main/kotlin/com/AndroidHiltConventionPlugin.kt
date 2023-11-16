package com

import com.najudoryeong.musicdo.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * 플러그인 for Hilt
 */
class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.plugins.kotlin.kapt.get().pluginId)
            apply(libs.plugins.dagger.hilt.android.get().pluginId)
        }

        dependencies {
            add("implementation", libs.dagger.hilt.android)
            add("kapt", libs.dagger.hilt.compiler)
        }
    }
}