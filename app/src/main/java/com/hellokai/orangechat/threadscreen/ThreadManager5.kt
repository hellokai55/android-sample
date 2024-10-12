package com.hellokai.orangechat.threadscreen

import android.util.Log
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock


/**
 * 两个线程Lock/Condition
 *
 */
class ThreadManager5 {

    class ABCLock(val times: Int) {
        val lock = ReentrantLock()
        val c1 = lock.newCondition()
        val c2 = lock.newCondition()
        val c3 = lock.newCondition()
        val list = listOf(c1, c2, c3)
        var state = 0

        fun printLog(name: String, targetState: Int, current: Condition, next: Condition) {
            var i = 0
            while (i < times) {
                lock.lock()
                try {
                    while (state % 3 != targetState) {
                        current.await()
                    }
                    state++
                    i++
                    Log.i("hellokaiaries", "printLog:${name}->$targetState")
                    next.signal()
                } finally {
                    lock.unlock()
                }
            }

        }

        fun test() {
            Thread({
                printLog("A", 0, c1, c2)
            }, "numThread").start()
            Thread({
                printLog("B", 1, c2, c3)
            }, "letterThread").start()
            Thread {
                printLog("C", 2, c3, c1)
            }.start()
        }

    }

    fun start() {
        val abcLock = ABCLock(10)
        abcLock.test()
    }

    fun stop() {

    }
}