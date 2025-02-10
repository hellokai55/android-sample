package com.hellokai.orangechat.customview

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hellokai.orangechat.R
import com.hellokai.orangechat.components.BarChart
import com.hellokai.orangechat.components.DragLayout
import com.hellokai.orangechat.components.FlowViewGroup
import com.hellokai.orangechat.components.HScrollLoadMoreView
import com.hellokai.orangechat.components.HencoderCustomView
import com.hellokai.orangechat.components.LikeVerticalViewPager
import com.hellokai.orangechat.components.ShowMoreTextView
import com.hellokai.orangechat.components.WaterView

@SuppressLint("SetTextI18n")
@Composable
fun CustomView8Screen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AndroidView(
            factory = { context: Context ->
                val v = LayoutInflater.from(context).inflate(R.layout.sliding_menu_layout, null)
                return@AndroidView v
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}