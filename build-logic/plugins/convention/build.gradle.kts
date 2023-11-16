plugins {
    `kotlin-dsl`
}

group = "com.najudoryeong.musicdo.build-logic"

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.spotless.gradle.plugin)
    compileOnly(libs.detekt.gradle.plugin)

    compileOnly(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "musicdo.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "musicdo.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "musicdo.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "musicdo.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidFeature") {
            id = "musicdo.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("hilt") {
            id = "musicdo.hilt"
            implementationClass = "HiltConventionPlugin"
        }
        register("androidHilt") {
            id = "musicdo.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("androidBenchmark") {
            id = "musicdo.android.benchmark"
            implementationClass = "AndroidBenchmarkConventionPlugin"
        }
        register("androidLint") {
            id = "musicdo.android.lint"
            implementationClass = "AndroidLintConventionPlugin"
        }
        register("androidSigningConfig") {
            id = "musicdo.android.signing-config"
            implementationClass = "AndroidSigningConfigConventionPlugin"
        }
        register("spotless") {
            id = "musicdo.spotless"
            implementationClass = "SpotlessConventionPlugin"
        }
        register("detekt") {
            id = "musicdo.detekt"
            implementationClass = "DetektConventionPlugin"
        }
        register("firebaseConfig") {
            id = "musicdo.firebase-config"
            implementationClass = "FirebaseConfigConventionPlugin"
        }
    }
}
