package com.hellokai.orangechat.threadscreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.awaitCancellation

@Composable
fun ThreadScreen4() {

    val scope = rememberCoroutineScope()
    DisposableEffect(scope) {
        val threadManager4 = ThreadManager4()
        threadManager4.start()
        onDispose {
            threadManager4.stop()
        }
    }
    Text("This is Thread Screen 4")
}