package com.hellokai.orangechat.customview

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.widget.TableLayout
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.hellokai.orangechat.R
import com.hellokai.orangechat.components.FragmentItemAdapter
import com.hellokai.orangechat.components.TabFragment

@SuppressLint("SetTextI18n")
@Composable
fun CustomView9Screen() {
    val context = LocalContext.current
    val fragmentManager = (context as? FragmentActivity)?.supportFragmentManager
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AndroidView(
            factory = { context: Context ->
                val v = LayoutInflater.from(context).inflate(R.layout.activity_nested_scrolling_parent2, null)
                val mTabLayout = v.findViewById<TabLayout>(R.id.tab_layout)
                val mViewPager = v.findViewById<ViewPager>(R.id.view_pager)

                mViewPager.adapter = FragmentItemAdapter(fragmentManager!!, initFragments(), initTitles())
                mViewPager.offscreenPageLimit = 4
                mTabLayout.setupWithViewPager(mViewPager)

                return@AndroidView v
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}

private fun initFragments(): List<Fragment> {
    val fragments: MutableList<Fragment> = ArrayList<Fragment>()
    for (i in 0 until 4) {
        fragments.add(TabFragment.newInstance("实现NestedScrollingParent2接口"))
    }
    return fragments
}

private fun initTitles(): List<String> {
    val titles: MutableList<String> = ArrayList()
    titles.add("首页")
    titles.add("全部")
    titles.add("作者")
    titles.add("专辑")
    return titles
}

