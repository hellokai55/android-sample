package com.hellokai.orangechat.customview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hellokai.orangechat.R
import com.hellokai.orangechat.components.CardConfig
import com.hellokai.orangechat.components.OverLayCardLayoutManager
import com.hellokai.orangechat.components.RenRenCallback

/**
 * 自定义柱状图，支持滑动
 */
@Composable
fun CustomView10Screen() {
    Box(
        modifier = Modifier.height(300.dp)
    ) {
        AndroidView(
            factory = { context: Context ->
                val recyclerView = RecyclerView(context)
                // 水平布局管理器
                val layoutManager = OverLayCardLayoutManager()

                // 创建测试数据（20个条目）
                val testData = List(20) { "Item ${it + 1}" }

                // 使用Android自带布局的Adapter
                val adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
                    // 创建ViewHolder时使用Android内置布局
                    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                        val view = LayoutInflater.from(parent.context)
                            .inflate(R.layout.custom_10_screen_list_item, parent, false)
                        return object : RecyclerView.ViewHolder(view) {}
                    }

                    // 绑定数据到TextView
                    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                        val textView = holder.itemView.findViewById<TextView>(R.id.text1)
                        textView.setBackgroundColor(if (position % 2 == 0) 0xFF00FF00.toInt() else 0xFF0000FF.toInt())
                        textView.text = testData[position]
                    }

                    override fun getItemCount() = testData.size
                }
                recyclerView.adapter = adapter
                recyclerView.layoutManager = layoutManager

                CardConfig.initConfig(context);

                val callback = RenRenCallback(recyclerView, adapter, testData)
                val itemTouchHelper = ItemTouchHelper(callback)
                itemTouchHelper.attachToRecyclerView(recyclerView)

                return@AndroidView recyclerView
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}