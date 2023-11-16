package com

import com.najudoryeong.musicdo.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * 플러그인 for hilt
 */
class HiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply(libs.plugins.kotlin.kapt.get().pluginId)

        dependencies {
            add("implementation", libs.dagger.hilt.core)
            add("kapt", libs.dagger.hilt.compiler)
        }
    }
}
