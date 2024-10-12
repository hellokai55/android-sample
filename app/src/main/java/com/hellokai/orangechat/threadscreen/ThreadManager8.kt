package com.hellokai.orangechat.threadscreen

import android.util.Log
import java.util.concurrent.locks.ReentrantLock

/**
 * 哲学家进餐问题
 */
class ThreadManager8 {

    fun start() {
        val c1 = Chopstick("1")
        val c2 = Chopstick("2")
        val c3 = Chopstick("3")
        val c4 = Chopstick("4")
        val c5 = Chopstick("5")

        Philosopher(c1, c2, "苏格拉底").start()
        Philosopher(c2, c3, "柏拉图").start()
        Philosopher(c3, c4, "亚里士多德").start()
        Philosopher(c4, c5, "赫拉克利特").start()
        Philosopher(c5, c1, "阿基米德").start()
    }

    fun stop() {
        // 如果需要，可以在这里加入逻辑以终止所有线程
    }

    class Chopstick(val name: String) : ReentrantLock() {
        override fun toString(): String {
            return "筷子${name}"
        }
    }

    class Philosopher(val left: Chopstick, val right: Chopstick, val name2: String) : Thread() {

        override fun run() {
            super.run()
            while (true) {
                if (left.tryLock()) {
                    try {
                        if (right.tryLock()) {
                            try {
                                Log.i("hellokaiaries", "$name2 is eating...")
                                Thread.sleep(500)
                            } finally {
                                right.unlock()
                            }
                        }
                    } finally {
                        left.unlock()
                    }
                }
            }
        }
    }
}