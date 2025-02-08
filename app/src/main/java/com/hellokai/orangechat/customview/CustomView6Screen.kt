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
import com.hellokai.orangechat.components.FlowViewGroup
import com.hellokai.orangechat.components.HScrollLoadMoreView
import com.hellokai.orangechat.components.HencoderCustomView
import com.hellokai.orangechat.components.LikeVerticalViewPager
import com.hellokai.orangechat.components.ShowMoreTextView
import com.hellokai.orangechat.components.WaterView

@SuppressLint("SetTextI18n")
@Composable
fun CustomView6Screen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AndroidView(
            factory = { context: Context ->
                val view = LayoutInflater.from(context).inflate(R.layout.hscroll_load_more_layout, null)
                view.findViewById<RecyclerView>(R.id.recycler_view).apply {
                    // 水平布局管理器
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

                    // 创建测试数据（20个条目）
                    val testData = List(20) { "Item ${it + 1}" }

                    // 使用Android自带布局的Adapter
                    adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
                        // 创建ViewHolder时使用Android内置布局
                        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                            val view = LayoutInflater.from(parent.context)
                                .inflate(android.R.layout.simple_list_item_1, parent, false)
                            return object : RecyclerView.ViewHolder(view) {}
                        }

                        // 绑定数据到TextView
                        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                            val textView = holder.itemView.findViewById<TextView>(android.R.id.text1)
                            textView.text = testData[position]

                            // 可选：设置item宽度（默认可能太窄）
                            holder.itemView.layoutParams = RecyclerView.LayoutParams(
                                RecyclerView.LayoutParams.WRAP_CONTENT,
                                RecyclerView.LayoutParams.MATCH_PARENT
                            ).apply {
                                width = 300  // 设置固定宽度确保水平滚动可见
                            }
                        }

                        override fun getItemCount() = testData.size
                    }
                }

                return@AndroidView view
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}