package com.hellokai.orangechat.threadscreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.awaitCancellation

@Composable
fun ThreadScreen3() {

    val scope = rememberCoroutineScope()
    DisposableEffect(scope) {
        val threadManager3 = ThreadManager3()
        threadManager3.start()
        onDispose {
            threadManager3.stop()
        }
    }
    Text("This is Thread Screen 3")
}