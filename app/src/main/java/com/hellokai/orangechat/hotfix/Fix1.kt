package com.hellokai.orangechat.hotfix

import android.util.Log

class Fix1 {
    companion object {
        fun testCrash() {
            Log.i("hellokaiaries", "testCrash start")
            val i = 1 / 0
            Log.i("hellokaiaries", "testCrash end")
        }
    }
}