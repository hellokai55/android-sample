package com.hellokai.orangechat.threadscreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.awaitCancellation

@Composable
fun ThreadScreen() {

    val scope = rememberCoroutineScope()
    DisposableEffect(scope) {
        val threadManager = ThreadManager()
        threadManager.start()
        onDispose {
            threadManager.stop()
        }
    }
    Text("This is Thread Screen")
}