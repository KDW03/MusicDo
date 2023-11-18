pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")

    }
}

rootProject.name = "MusicDo"
include(":app")

include(":core:common")
include(":core:data")
include(":core:datastore")
include(":core:datastore-test")
include(":core:mediastore")
include(":core:domain")
include(":core:ui")
include(":core:designsystem")
include(":core:model")
include(":core:media-common")
include(":core:media-service")
include(":core:media-notification")
include(":core:permission")
include(":core:testing")
include(":core:database")

include(":features:home")
include(":features:search")
include(":features:favorite")
include(":features:settings")
include(":features:player")
include(":features:library")
include(":features:playlist")

include(":lint")

