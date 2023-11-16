package com

import com.najudoryeong.musicdo.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * 플러그인 for feature
 * -> core-ui
 */
class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("musicdo.android.library")
            apply("musicdo.android.library.compose")
            apply("musicdo.android.hilt")
        }

        dependencies {
            add("implementation", project(":core:core-ui"))

            add("implementation", libs.androidx.lifecycle.runtime.compose)
            add("implementation", libs.androidx.lifecycle.viewmodel.compose)
            add("implementation", libs.androidx.hilt.navigation.compose)
            add("implementation", libs.kotlinx.coroutines.android)
        }
    }
}
