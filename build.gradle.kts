buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.gms) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.roborazzi) apply false
    alias(libs.plugins.secrets) apply false
    alias(libs.plugins.android.test) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
}

tasks.register("printDirectModuleDependencies") {
    doLast {
        val directDependencies = mutableMapOf<String, MutableSet<String>>()

        allprojects.forEach { project ->
            project.configurations.findByName("implementation")?.allDependencies?.forEach { dependency ->
                if (dependency is org.gradle.api.artifacts.ProjectDependency) {
                    directDependencies.computeIfAbsent(project.name) { mutableSetOf() }.add(dependency.dependencyProject.name)
                }
            }
        }

        // Remove indirect dependencies
        directDependencies.forEach { (project, dependencies) ->
            val toRemove = mutableSetOf<String>()
            dependencies.forEach { dependency ->
                toRemove.addAll(directDependencies[dependency] ?: emptySet())
            }
            dependencies.removeAll(toRemove)
        }

        // Print direct dependencies only
        directDependencies.forEach { (project, dependencies) ->
            dependencies.forEach { dependency ->
                println("$project -> $dependency")
            }
        }
    }
}
