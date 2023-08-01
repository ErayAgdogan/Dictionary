pluginManagement {

    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    includeBuild("build-logic")
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }

}
rootProject.name = "Dictionary"
include(":app")

include(":core:network")
include(":core:model")
include(":core:database")
include(":core:data")
include(":core:connectivity")
include(":core:design")
include(":core:ui")
include(":core:datastore")

include(":feature:search")
include(":feature:settings")
include(":core:theme")
include(":feature:bookmark")
