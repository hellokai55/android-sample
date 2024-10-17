pluginManagement {
//    includeBuild("build-logic")
    repositories {
        maven {
            url = uri("https://maven.aliyun.com/nexus/content/groups/public/")
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven {
            url = uri("https://maven.aliyun.com/nexus/content/groups/public/")
        }
        google()
        mavenCentral()
    }
    
}

rootProject.name = "OrangeChat"
include(":app")
include(":app:nativelib")
