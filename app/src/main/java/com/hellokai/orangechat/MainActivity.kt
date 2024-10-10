package com.hellokai.orangechat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.hellokai.orangechat.components.FunctionItem
import com.hellokai.orangechat.ui.theme.OrangeChatTheme

class MainActivity : ComponentActivity() {

    private val funcList = listOf(
        FunctionName("多线程顺序打印")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OrangeChatTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        funcList,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

data class FunctionName(val name: String)

@Composable
fun App() {
    val navController = rememberNavController()

}

@Composable
fun Greeting(funcList: List<FunctionName>, modifier: Modifier = Modifier) {
    // 列表组件
    LazyColumn(modifier) {
        itemsIndexed(funcList) { index, item ->
            FunctionItem(index, item) {

            }
        }
    }
}