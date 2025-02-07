package com.hellokai.orangechat.components

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Scroller
import kotlin.math.abs

class LikeVerticalViewPager(context: Context, attributeSet: AttributeSet? = null): ViewGroup(context, attributeSet) {
    // 当前页面索引
    private var currentPage = 0
    // 最小滑动速度阈值
    private val minFlingVelocity = 100
    // 最小滑动距离阈值（屏幕高度的1/3）
    private var minScrollDistance = 0

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        for(i in 0 until childCount) {
            val child = getChildAt(i)
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
        }
        // 初始化最小滑动距离
        minScrollDistance = measuredHeight / 3
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        var childTop = 0
        for(i in 0 until childCount) {
            val child = getChildAt(i)
            val childHeight = child.measuredHeight
            child.layout(0, childTop, measuredWidth, childTop + childHeight)
            childTop += childHeight
        }
    }

    private var startY = 0f
    private var startScrollY = 0

    private val gestureDetector = GestureDetector(context, object: GestureDetector.SimpleOnGestureListener() {
        override fun onFling(e1: MotionEvent?, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            if (abs(velocityY) > minFlingVelocity) {
                if (velocityY > 0 && currentPage > 0) {
                    // 向上滑，显示上一页
                    smoothScrollToPage(currentPage - 1)
                } else if (velocityY < 0 && currentPage < childCount - 1) {
                    // 向下滑，显示下一页
                    smoothScrollToPage(currentPage + 1)
                }
            } else {
                // 速度不够，回到当前页
                smoothScrollToPage(currentPage)
            }
            return true
        }

        override fun onScroll(e1: MotionEvent?, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
            // 限制滚动范围
            val newScrollY = scrollY + distanceY.toInt()
            if (newScrollY >= 0 && newScrollY <= (childCount - 1) * height) {
                scrollBy(0, distanceY.toInt())
            }
            return true
        }

        override fun onDown(e: MotionEvent): Boolean {
            return true
        }
    })

    private val scroller = Scroller(context)

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    override fun computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(0, scroller.currY)
            postInvalidate()
        }
    }

    private fun smoothScrollToPage(page: Int) {
        currentPage = page
        val targetY = page * height
        val dy = targetY - scrollY
        scroller.startScroll(0, scrollY, 0, dy, 300) // 300ms的滚动动画
        postInvalidate()
    }
}