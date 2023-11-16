package com

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.android.build.gradle.internal.dsl.SigningConfig
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import java.util.Properties

/**
 * 플러그인 for 릴리즈 빌드 서명 설정
 */
class AndroidSigningConfigConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        val keystoreProperties = Properties()
        val keystorePropertiesFile = rootProject.file("keystore.properties")
        if (keystorePropertiesFile.exists() && keystorePropertiesFile.isFile) {
            keystorePropertiesFile.inputStream().use { input ->
                keystoreProperties.load(input)
            }
        }

        extensions.configure<BaseAppModuleExtension> {
            val signingConfig = if (keystorePropertiesFile.exists()) {
                createSigningConfigFromProperties(
                    target = this@with,
                    name = "release",
                    properties = keystoreProperties
                )
            } else {
                signingConfigs.getByName("debug")
            }

            defaultConfig {
                buildTypes {
                    release {
                        this.signingConfig = signingConfig
                    }
                }
            }
        }
    }
}

private fun BaseAppModuleExtension.createSigningConfigFromProperties(
    target: Project,
    name: String,
    properties: Properties
): SigningConfig {
    val keystore = mapOf(
        "keyAlias" to properties.getProperty("keyAlias"),
        "keyPassword" to properties.getProperty("keyPassword"),
        "storeFile" to properties.getProperty("storeFile"),
        "storePassword" to properties.getProperty("storePassword")
    )

    keystore.forEach { (key, value) ->
        requireNotNull(value) { "$key is null." }
    }

    return signingConfigs.create(name) {
        keyAlias = keystore.getValue("keyAlias")
        keyPassword = keystore.getValue("keyPassword")
        storeFile = target.file(keystore.getValue("storeFile"))
        storePassword = keystore.getValue("storePassword")
    }
}