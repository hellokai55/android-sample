package com.hellokai.orangechat

import com.android.build.api.artifact.ScopedArtifact
import com.android.build.api.artifact.SingleArtifact
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.api.variant.ScopedArtifacts
import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.BaseVariant
import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFile
import org.gradle.api.provider.ListProperty
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import org.gradle.configurationcache.extensions.capitalized
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.register
import java.io.File
import java.io.FileInputStream
import java.security.MessageDigest


class PatchPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        println("This is Patch Plugin")
//        val android = project.extensions.getByType(AppExtension::class.java)
//        var applicationVariant: BaseVariant? = null
//        android.applicationVariants.configureEach { variant ->
//            if (variant.name == "debug") {
//                applicationVariant = variant
//                println("hellokai applicationVariants configureEach")
//            }
//        }
        project.plugins.withId("com.android.application") {
            val androidComponents =
                project.extensions.getByType(ApplicationAndroidComponentsExtension::class.java)
            // Registers a callback to be called, when a new variant is configured
            androidComponents.onVariants { variant ->
                val taskName = "check${variant.name.capitalized()}AsmTransformation"
                val taskProvider = project.tasks.register<ModifyClassesTask>(taskName) {
                    output.set(
                        project.layout.buildDirectory.dir("intermediates/$taskName")
                    )
                }

//                taskProvider.configure {
//                    it.dependsOn(applicationVariant?.assembleProvider)
//                }

                // Register modify classes task
                variant.artifacts.forScope(ScopedArtifacts.Scope.PROJECT)
                    .use(taskProvider)
                    .toGet(
                        ScopedArtifact.CLASSES,
                        ModifyClassesTask::projectJars,
                        ModifyClassesTask::projectDirectories,
                    )
            }
        }
    }
}

abstract class ModifyClassesTask : DefaultTask() {
    // In order for the task to be up-to-date when the inputs have not changed,
    // the task must declare an output, even if it's not used. Tasks with no
    // output are always run regardless of whether the inputs changed
    @get:OutputDirectory
    abstract val output: DirectoryProperty

    /**
     * Project scope, not including dependencies.
     */
    @get:InputFiles
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val projectDirectories: ListProperty<Directory>

    /**
     * Project scope, not including dependencies.
     */
    @get:InputFiles
    @get:PathSensitive(PathSensitivity.NONE)
    abstract val projectJars: ListProperty<RegularFile>

    @TaskAction
    fun taskAction() {
        val patchDir = output

        // 确保目录存在
        if (!patchDir.get().asFile.exists()) {
            println("Creating patch directory")
            patchDir.get().asFile.mkdirs()
        }

        val existMap = mutableMapOf<String, String>()

        // 创建 patch.txt 文件
        val patchHash = patchDir.get().file("patch.txt")
        val isExist = patchHash.asFile.exists()
        if (!isExist) {
            println("Creating patch file")
            patchHash.asFile.createNewFile()
        } else {
            // 获取所有文件变成map,key是path,value是hash
            patchHash.asFile.readLines().map {
                val split = it.split(":")
                split[0] to split[1]
            }.forEach {
                println("ModifyClassesTask existMap,key:${it.first} -> ${it.second}")
                existMap[it.first] = it.second
            }
        }

        patchHash.asFile.writeText("")

        // 先删除这里面的所有文件，除了patch.txt
        patchDir.get().asFile.listFiles()?.forEach {
            if (it.name != "patch.txt") {
                it.deleteRecursively()
            }
        }

        // 遍历projectDirectories
        projectDirectories.get().forEach { directory ->
            directory.asFile.walkTopDown().forEach { file ->
                if (file.isFile
                    && file.extension == "class"
                    && !file.path.contains("/R\$")
                    && !file.path.contains("/R.class")
                    && !file.path.endsWith("/BuildConfig.class")
                    && file.path.contains("com/hellokai/orangechat")
                ) {
                    val md5 = calculateFileHash(file)
                    // 先清空,再将path:hash写入patch.hash文件
                    patchHash.asFile.appendText("${file.path}:$md5\n")
                    println("ModifyClassesTask path 2234-> ${file.path} equals ${existMap[file.path] == md5}")

                    if (existMap[file.path] == md5) { //如果md5值相同，代表没修改过，不用处理
                        return
                    }
                    // 如果MD5不同，则把文件写到patch目录，文件名应该是从file.path中截取com/hellokai/orangechat/xxx到最后作为文件名，让其文件名是com/hellokai/orangechat/这样的
                    val relativePath = "com/hellokai/orangechat/" + file.path.substringAfter("com/hellokai/orangechat/")
                    // 按照patch目录的相对路径创建文件，并最后把文件内容写入
                    val patchFile = patchDir.get().file(relativePath)
                    patchFile.asFile.parentFile.mkdirs()
                    file.copyTo(patchFile.asFile)
                }
            }
        }
    }

    fun calculateFileHash(file: File): String {
        val digest = MessageDigest.getInstance("SHA-256")
        FileInputStream(file).use { fis ->
            val byteArray = ByteArray(4096)
            var bytesRead: Int
            while (fis.read(byteArray).also { bytesRead = it } != -1) {
                digest.update(byteArray, 0, bytesRead)
            }
        }
        val hashBytes = digest.digest()
        return hashBytes.joinToString("") { "%02x".format(it) }
    }
}
