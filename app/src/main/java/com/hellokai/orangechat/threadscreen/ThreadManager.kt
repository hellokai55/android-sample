package com.hellokai.orangechat.threadscreen

import android.util.Log
import java.util.concurrent.locks.ReentrantLock
import kotlin.jvm.Throws


/**
 * 多线程顺序打印
 */
class ThreadManager {

    class ABCLock(val times: Int) {
        var state = 0
        val lock = ReentrantLock()

        fun printLog(name: String, targetState: Int) {
            var i = 0;
            while (i < times) {
                lock.lock()
                try {
                    if (state % 3 == targetState) {
                        state++
                        i++
                        Log.i("hellokaiaries", "printLog:$name")
                    }
                } finally {
                    lock.unlock()
                }
            }
        }
    }

    fun start() {
        val abcLock = ABCLock(1000)
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