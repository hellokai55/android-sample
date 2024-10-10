package com.hellokai.orangechat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hellokai.orangechat.FunctionName

@Composable
fun FunctionItem(index: Int, item: FunctionName, onItemClick: () -> Unit) {
    Column(modifier = Modifier.clickable { onItemClick() }) {
        Text(
            text = "${index + 1}. ${item.name}",
            style = TextStyle(fontSize = 20.sp),
            modifier = Modifier
                .height(50.dp)
                .wrapContentHeight(),

            )
        HorizontalDivider(
            modifier = Modifier
                .background(color = Color.Black)
                .height(1.dp)
        )
    }
}