package com.hellokai.orangechat.threadscreen

import android.util.Log
import java.util.concurrent.locks.ReentrantLock
import kotlin.jvm.Throws


/**
 * 两个线程交替打印奇数和偶数
 */
class ThreadManager3 {

    class ABCLock(var count: Int, val times: Int) {
        val lock = Object()

        fun printLog() {
            synchronized(lock) {
                while (count<times) {
                    try {
                        Log.i("hellokaiaries", "printLog:${Thread.currentThread().name}->${count++}")
                        lock.notifyAll()
                        lock.wait()
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
                lock.notifyAll()
            }
        }

    }

    fun start() {
        val abcLock = ABCLock(0,10)
        Thread({
            abcLock.printLog()
        }, "odd").start()
        Thread({
            abcLock.printLog()
        }, "even").start()
    }

    fun stop() {

    }
}