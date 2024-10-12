package com.hellokai.orangechat.threadscreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.awaitCancellation

@Composable
fun ThreadScreen6() {

    val scope = rememberCoroutineScope()
    DisposableEffect(scope) {
        val threadManager6 = ThreadManager6()
        threadManager6.start()
        onDispose {
            threadManager6.stop()
        }
    }
    Text("This is Thread Screen 6")
}