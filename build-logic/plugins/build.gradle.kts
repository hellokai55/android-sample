plugins {
    `java-gradle-plugin`
    alias(libs.plugins.kotlin.jvm)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    implementation(gradleKotlinDsl())
}

gradlePlugin {
    plugins {
        create("customPlugin") {
            id = "com.hellokai.hotpatch"
            implementationClass = "com.hellokai.orangechat.PatchPlugin"
        }
    }
}