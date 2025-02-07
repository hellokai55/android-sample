package com.hellokai.orangechat.customview

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.hellokai.orangechat.components.BarChart
import com.hellokai.orangechat.components.FlowViewGroup
import com.hellokai.orangechat.components.HencoderCustomView
import com.hellokai.orangechat.components.WaterView

@SuppressLint("SetTextI18n")
@Composable
fun CustomView4Screen() {
    Box(
        modifier = Modifier.height(500.dp)
    ) {
        AndroidView(
            factory = { context: Context ->
                FlowViewGroup(context).apply {
                    // 添加10个子view，宽高都是100dp的
                    for (i in 0 until 10) {
                        val view = TextView(context)
                        view.text = "Hello $i"
                        // 随机颜色
                        view.background = if (i % 2 == 0) {
                            context.getDrawable(android.R.color.holo_blue_light)
                        } else {
                            context.getDrawable(android.R.color.holo_red_light)
                        }
                        addView(view, (i + 1) * 50, 200)
                    }
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}