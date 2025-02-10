package com.hellokai.orangechat.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
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
fun CustomView7Screen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AndroidView(
            factory = { context: Context ->
                val v = DragLayout(context)

                val child1 = View(context)
                child1.setBackgroundColor(Color.parseColor("#ff5722"))
                child1.id = R.id.card
                child1.setOnClickListener {
                    Toast.makeText(context, "card click", Toast.LENGTH_SHORT).show()
                }
                v.addView(child1, FrameLayout.LayoutParams(200.dp.value.toInt(), 200.dp.value.toInt()))

                val child2 = View(context)
                child2.setBackgroundColor(Color.parseColor("#2196f3"))
                val child2Lp = FrameLayout.LayoutParams(100.dp.value.toInt(), 100.dp.value.toInt())
                child2Lp.gravity = Gravity.CENTER_VERTICAL
                child2.id = R.id.edge_menu
                child2.setOnClickListener {
                    Toast.makeText(context, "edge_menu click", Toast.LENGTH_SHORT).show()
                }
                v.addView(child2, child2Lp)

                return@AndroidView v
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}