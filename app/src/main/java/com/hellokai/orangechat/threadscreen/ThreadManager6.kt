package com.hellokai.orangechat.threadscreen

import android.util.Log
import java.util.concurrent.locks.ReentrantLock


/**
 *
 * 顺序打印Foor,Bar
 */
class ThreadManager6 {

    class ABCLock(val times: Int) {
        val lock = ReentrantLock()
        val c1 = lock.newCondition()
        val c2 = lock.newCondition()
        var state = 0

        fun printFoor() {
            var i = 0
            while (i < times) {
                lock.lock();
                try {

                    while ( state % 2 == 1) {
                        c1.await()
                    }
                    state++
                    Log.i("hellokaiaries", "Foor")
                    c2.signal()
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } finally {
                    lock.unlock()
                }
                i++
            }
        }

        fun printBar() {
            var i = 0
            while (i < times) {
                lock.lock();
                try {
                    while (state % 2 == 0) {
                        c2.await()
                    }
                    Log.i("hellokaiaries", "Bar")
                    c1.signal()
                    state++
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } finally {
                    lock.unlock()
                }
                i++
            }
        }

        fun test() {
            Thread({
                printFoor()
            }, "Foor").start()
            Thread({
                printBar()
            }, "Bar").start()
        }

    }

    fun start() {
        val abcLock = ABCLock(10)
        abcLock.test()
    }

    fun stop() {

    }
}