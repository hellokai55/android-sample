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
import com.hellokai.orangechat.components.HencoderCustomView

/**
 * https://rengwuxian.com/ui-1-1/
 * 自定义view
 */
@Composable
fun CustomView2Screen() {
    Box(
        modifier = Modifier.height(500.dp)
    ) {
        AndroidView(
            factory = { context: Context ->
                HencoderCustomView(context)
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}