package com.hellokai.orangechat.threadscreen

import android.util.Log
import java.util.concurrent.locks.ReentrantLock
import kotlin.jvm.Throws


/**
 * 多线程顺序打印
 */
class ThreadManager2 {

    class ABCLock(val times: Int) {
        var state = 0
        val lock = Object()

        fun printLog(name: String, targetState: Int){
            var i = 0
            while (i < times) {
                synchronized(lock) {
                    while (state % 3 != targetState) {
                        try {
                            lock.wait()
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                    }
                    state++
                    Log.i("hellokaiaries", "printLog:$name,i:$i")
                    i++
                    lock.notifyAll()
                }
            }

        }

    }

    fun start() {
        val abcLock = ABCLock(10)
        Thread {
            abcLock.printLog("A", 0)
        }.start()
        Thread {
            abcLock.printLog("B", 1)
        }.start()
        Thread {
            abcLock.printLog("C", 2)
        }.start()
    }

    fun stop() {

    }
}