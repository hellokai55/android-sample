package com.hellokai.orangechat.threadscreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope

@Composable
fun ThreadScreen8() {

    val scope = rememberCoroutineScope()
    DisposableEffect(scope) {
        val threadManager8 = ThreadManager8()
        threadManager8.start()
        onDispose {
            threadManager8.stop()
        }
    }
    Text("This is Thread Screen 8")
}