pluginManagement {
    includeBuild("build-logic")
    repositories {
        maven { url = uri("https://mirrors.ustc.edu.cn/maven/repository/") }
        maven { url = uri("https://www.jitpack.io") }
        maven { url = uri("https://maven.google.com") }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven { url = uri("https://mirrors.ustc.edu.cn/maven/repository/") }
        maven { url = uri("https://www.jitpack.io") }
        maven { url = uri("https://maven.google.com") }
        google()
        mavenCentral()
    }
    
}

rootProject.name = "OrangeChat"
include(":app")
include(":app:nativelib")
