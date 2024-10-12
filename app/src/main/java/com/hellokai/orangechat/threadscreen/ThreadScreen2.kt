package com.hellokai.orangechat.threadscreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.awaitCancellation

@Composable
fun ThreadScreen2() {

    val scope = rememberCoroutineScope()
    DisposableEffect(scope) {
        val threadManager2 = ThreadManager2()
        threadManager2.start()
        onDispose {
            threadManager2.stop()
        }
    }
    Text("This is Thread Screen 2")
}