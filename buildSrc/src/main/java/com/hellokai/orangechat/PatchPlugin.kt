package com.hellokai.orangechat
import org.gradle.api.Plugin
import org.gradle.api.Project

class PatchPlugin: Plugin<Project> {
    override fun apply(project: Project) {
        println("This is Patch Plugin")
        project.tasks.register("myCustomTask") {
            doLast {
                println("This is my custom task!")
            }
        }
    }
}