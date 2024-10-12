package com.hellokai.orangechat.threadscreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope

@Composable
fun ThreadScreen7() {

    val scope = rememberCoroutineScope()
    DisposableEffect(scope) {
        val threadManager7 = ThreadManager7()
        threadManager7.start()
        onDispose {
            threadManager7.stop()
        }
    }
    Text("This is Thread Screen 7")
}