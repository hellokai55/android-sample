package com.hellokai.orangechat.hotfix

import android.content.Context
import dalvik.system.DexClassLoader
import java.io.File
import java.io.FileOutputStream

object HotFixManager {

    fun init(context: Context) {
        val dexDir = File(context.filesDir, "hotfix")
        if (!dexDir.exists()) {
            dexDir.mkdir()
        }
        val targetFile = File(dexDir, "app-debug.apk")

        if (targetFile.exists()) {
            targetFile.delete()
        }

        val assetManager = context.assets
        assetManager.open("app-debug.apk").use { inputStream ->
            FileOutputStream(targetFile).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        targetFile.setReadOnly()
        loadPath(targetFile.absolutePath, dexDir.absolutePath)
    }

    fun loadPath(dexPath: String, odexPath: String) {
        val pathClassLoader = HotFixManager::class.java.classLoader
        val dexClassLoader = DexClassLoader(dexPath, odexPath, dexPath, pathClassLoader)
        val baseDexElements = getDexElements(getPathList(pathClassLoader!!)!!) as Array<*>
        val newDexElements = getDexElements(getPathList(dexClassLoader)!!) as Array<*>

        val elementType = baseDexElements.javaClass.componentType
        val allDexElements = java.lang.reflect.Array.newInstance(elementType, baseDexElements.size + newDexElements.size) as Array<*>

        System.arraycopy(newDexElements, 0, allDexElements, 0, newDexElements.size)
        System.arraycopy(baseDexElements, 0, allDexElements, newDexElements.size, baseDexElements.size)

        val pathList = getPathList(pathClassLoader)
        setFiled(pathList!!, pathList.javaClass, "dexElements", allDexElements)
    }

    fun getPathList(baseDexClassLoader: Any): Any? {
        return getFiled(baseDexClassLoader, Class.forName("dalvik.system.BaseDexClassLoader"), "pathList")
    }

    fun getDexElements(paramObject: Any): Any? {
        return getFiled(paramObject, paramObject.javaClass, "dexElements")
    }

    fun getFiled(obj: Any, clazz: Class<*>, filed: String): Any? {
        val field = clazz.getDeclaredField(filed)
        field.isAccessible = true
        return field.get(obj)
    }

    fun setFiled(obj: Any, clazz: Class<Any>, filed: String, value: Any) {
        val field = clazz.getDeclaredField(filed)
        field.isAccessible = true
        field.set(obj, value)
    }

}