package com.hellokai.orangechat.threadscreen

import android.util.Log


/**
 * 两个线程交替打印奇数和偶数
 */
class ThreadManager4 {

    class ABCLock(var count: Int, val times: Int) {
        val lock = Object()
        var i = 0

        fun printLog() {
            synchronized(lock) {
                while (i < 26) {
                    if (Thread.currentThread().name == "numThread") {
                        Log.i("hellokaiaries", "${i+1}")
                        lock.notifyAll()
                        try {
                            lock.wait()
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                    } else if (Thread.currentThread().name == "letterThread") {
                        Log.i("hellokaiaries", "${('A' + i).toChar()}")
                        lock.notifyAll()
                        try {
                            lock.wait()
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                    }
                    i++
                }
                lock.notifyAll()
            }
        }
    }

    fun start() {
        val abcLock = ABCLock(0, 10)
        Thread({
            abcLock.printLog()
        }, "numThread").start()
        Thread({
            abcLock.printLog()
        }, "letterThread").start()
    }

    fun stop() {

    }
}