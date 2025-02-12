package com.hellokai.orangechat.components

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.NestedScrollingParent2
import androidx.core.view.NestedScrollingParentHelper
import androidx.core.view.updateLayoutParams
import com.hellokai.orangechat.R

class NestedScrollParent2Layout(context: Context, attributeSet: AttributeSet? = null
): LinearLayout(context, attributeSet), NestedScrollingParent2 {

    lateinit var mTopView: View
    lateinit var mNavView: View
    lateinit var mViewPager: View
    private var mTopViewHeight: Int = 0

    private val mNextedScrollingParentHelper = NestedScrollingParentHelper(this)

    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        return axes and View.SCROLL_AXIS_VERTICAL != 0
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
        mNextedScrollingParentHelper.onNestedScrollAccepted(child, target, axes, type)
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        android.util.Log.d("NestedScrollParent2", "onNestedPreScroll: dy = $dy")
        val hideTop = dy > 0 && scrollY < mTopViewHeight
        val showTop = dy < 0 && scrollY >= 0 && !target.canScrollVertically(-1)
        if (hideTop || showTop) {
            scrollBy(0, dy)
            consumed[1] = dy
        }
    }

    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
//        android.util.Log.d("NestedScrollParent2", "onNestedScroll: dyConsumed = $dyConsumed, dyUnconsumed = $dyUnconsumed")
        if (dyUnconsumed < 0) {
            scrollBy(0, dyUnconsumed)
        }
    }

    override fun onStopNestedScroll(target: View, type: Int) {
        mNextedScrollingParentHelper.onStopNestedScroll(target, type)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mViewPager.updateLayoutParams<LinearLayout.LayoutParams> {
            height = measuredHeight - mNavView.measuredHeight
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        mTopView = findViewById(R.id.iv_head_image)
        mNavView = findViewById(R.id.tab_layout)
        mViewPager = findViewById(R.id.view_pager)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mTopViewHeight = mTopView.measuredHeight
    }

    override fun scrollTo(x: Int, y: Int) {
        var resultY = y
        if (y < 0) {
            resultY = 0
        }
        if (y > mTopViewHeight) {
            resultY = mTopViewHeight
        }
        super.scrollTo(x, resultY)
    }

}