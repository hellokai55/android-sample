package com.hellokai.orangechat.threadscreen
import android.util.Log
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock

class ThreadManager7 {

    class ABCLock {
        private val lock = ReentrantLock()
        private val hCondition: Condition = lock.newCondition()
        private val oCondition: Condition = lock.newCondition()
        @Volatile
        var flag = true
        var i = 0

        fun releaseH() {
            lock.lock()
            try {
                while(!flag) {
                    hCondition.await()
                }
                Log.i("hellokaiaries", "H")
                i++
                if (i % 2 == 0) {
                    flag = false
                    oCondition.signalAll()
                }
            } finally {
                lock.unlock()
            }
        }

        fun releaseO() {
            lock.lock()
            try {
                while (flag) {
                    oCondition.await()
                }
                Log.i("hellokaiaries", "O")
                flag = true
                hCondition.signalAll()
            } finally {
                lock.unlock()
            }
        }
    }

    fun start() {
        val input = "OOHHHH"
        val hCount = input.count { it == 'H' }
        val oCount = input.count { it == 'O' }

        val abcLock = ABCLock()

        repeat(hCount) {
            Thread {
                abcLock.releaseH()
            }.start()
        }

        repeat(oCount) {
            Thread {
                abcLock.releaseO()
            }.start()
        }
    }

    fun stop() {
        // 如果需要，可以在这里加入逻辑以终止所有线程
    }
}