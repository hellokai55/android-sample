package com.hellokai.orangechat.customview

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
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
import com.hellokai.orangechat.components.LikeVerticalViewPager
import com.hellokai.orangechat.components.WaterView

@SuppressLint("SetTextI18n")
@Composable
fun CustomView5Screen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AndroidView(
            factory = { context: Context ->
                LikeVerticalViewPager(context).apply {
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
                        addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                    }
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}