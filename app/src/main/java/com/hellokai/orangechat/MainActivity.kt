package com.hellokai.orangechat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hellokai.orangechat.components.FunctionItem
import com.hellokai.orangechat.customview.CustomView1Screen
import com.hellokai.orangechat.customview.CustomView2Screen
import com.hellokai.orangechat.customview.CustomView3Screen
import com.hellokai.orangechat.customview.CustomView4Screen
import com.hellokai.orangechat.hotfix.HotFixScreen
import com.hellokai.orangechat.threadscreen.ThreadScreen
import com.hellokai.orangechat.threadscreen.ThreadScreen2
import com.hellokai.orangechat.threadscreen.ThreadScreen3
import com.hellokai.orangechat.threadscreen.ThreadScreen4
import com.hellokai.orangechat.threadscreen.ThreadScreen5
import com.hellokai.orangechat.threadscreen.ThreadScreen6
import com.hellokai.orangechat.threadscreen.ThreadScreen7
import com.hellokai.orangechat.threadscreen.ThreadScreen8
import com.hellokai.orangechat.ui.theme.OrangeChatTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OrangeChatTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { p ->
                    App(p)
                }
            }
        }
    }
}

data class FunctionName(val name: String, val route: String)

private val funcList = listOf(
    FunctionName("多线程顺序打印", "thread1"),
    FunctionName("多线程顺序打印2", "thread2"),
    FunctionName("2个线程交替打印奇数和偶数", "thread3"),
    FunctionName("2个线程一个输出字母，一个输出数字", "thread4"),
    FunctionName("多线程顺序打印5", "thread5"),
    FunctionName("多线程交替打印6", "thread6"),
    FunctionName("多线程H2O打印7", "thread7"),
    FunctionName("哲学家进餐问题", "thread8"),
    FunctionName("HotFix", "hotfix"),
    FunctionName("自定义柱状图", "customView1"),
    FunctionName("自定义view-hencoder", "customView2"),
    FunctionName("自定义view-水波纹", "customView3"),
    FunctionName("自定义viewgroup-流式布局", "customView4"),
)

val LocalNavController = staticCompositionLocalOf<NavController?> { null }

@Composable
fun App(padding: PaddingValues) {
    val navController = rememberNavController()
    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(navController, startDestination = "home", modifier = Modifier.padding(padding)) {
            composable("home") {
                Greeting(funcList = funcList)
            }
            composable("thread1") {
                ThreadScreen()
            }
            composable("thread2") {
                ThreadScreen2()
            }
            composable("thread3") {
                ThreadScreen3()
            }
            composable("thread4") {
                ThreadScreen4()
            }
            composable("thread5") {
                ThreadScreen5()
            }
            composable("thread6") {
                ThreadScreen6()
            }
            composable("thread7") {
                ThreadScreen7()
            }
            composable("thread8") {
                ThreadScreen8()
            }
            composable("hotfix") {
                HotFixScreen()
            }
            composable("customView1") {
                CustomView1Screen()
            }
            composable("customView2") {
                CustomView2Screen()
            }
            composable("customView3") {
                CustomView3Screen()
            }
            composable("customView4") {
                CustomView4Screen()
            }
        }
    }
}

@Composable
fun Greeting(funcList: List<FunctionName>, modifier: Modifier = Modifier) {
    // 列表组件
    val navController = LocalNavController.current
    LazyColumn(modifier) {
        itemsIndexed(funcList) { index, item ->
            FunctionItem(index, item) {
                navController?.navigate(item.route)
            }
        }
    }
}