package com.hellokai.orangechat.customview

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.hellokai.orangechat.components.BarChart

/**
 * 自定义柱状图，支持滑动
 */
@Composable
fun CustomView1Screen() {
    Box(
        modifier = Modifier.height(300.dp)
    ) {
        AndroidView(
            factory = { context: Context ->
                BarChart(context).also {
                    it.testSetBarInfoList()
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}