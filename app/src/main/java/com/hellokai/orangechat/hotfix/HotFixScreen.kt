package com.hellokai.orangechat.hotfix

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.hellokai.orangechat.hotfix.patch.Fix2

@Composable
fun HotFixScreen() {
    LaunchedEffect(Unit) {
        Fix2.testCrash()
    }
    Text("This is HotFixScreen: ${Fix2.testCrash()}")
}