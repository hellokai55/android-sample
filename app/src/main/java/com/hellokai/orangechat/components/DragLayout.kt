package com.hellokai.orangechat.components

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.customview.widget.ViewDragHelper
import com.hellokai.orangechat.R
import kotlin.math.min

/**
 * 可拖拽子view
 */
class DragLayout(context: Context, attributeSet: AttributeSet? = null) : FrameLayout(context, attributeSet) {

    val mDragHelperCallback = object : ViewDragHelper.Callback() {
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return child.id == R.id.card
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            // 限制水平推拽范围在父容器内
            val padding = paddingLeft
            return Math.max(padding, Math.min(left, width - child.width - padding))
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            return Math.max(paddingTop, Math.min(top, height - child.height - paddingBottom))
        }

        override fun getViewHorizontalDragRange(child: View): Int {
            return width - child.width
        }

        override fun getViewVerticalDragRange(child: View): Int {
            return height - child.height
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            // 平滑移动捕获的view到目标位置
//            mDragHelper.settleCapturedViewAt(0, releasedChild.top)
//            invalidate()

            if (releasedChild.id == R.id.card) {
                val minLeft = paddingLeft
                val maxLeft = width - paddingRight - releasedChild.width
                val minTop = paddingTop
                val maxTop = height - paddingBottom - releasedChild.height
//                mDragHelper.flingCapturedView((maxLeft - minLeft) / 2 - 100, (maxTop - minTop) / 2, (maxLeft - minLeft) / 2, (maxTop - minTop) / 2)

//                if (!mDragHelper.continueSettling(true)) {
//                    mDragHelper.settleCapturedViewAt(0, top)
//                }
                val centerX = (maxLeft - minLeft) / 2
                val centerY = (maxTop - minTop) / 2
                mDragHelper.settleCapturedViewAt(centerX, centerY)
            }
            invalidate()
        }

        override fun onEdgeDragStarted(edgeFlags: Int, pointerId: Int) {
            val edgeView = findViewById<View>(R.id.edge_menu)
            // 强制捕获edgeView，开始拖拽
            mDragHelper.captureChildView(edgeView, pointerId)
        }
    }

    val mDragHelper: ViewDragHelper = ViewDragHelper.create(this, 1.0f, mDragHelperCallback)

    init {
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return mDragHelper.shouldInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mDragHelper.processTouchEvent(event!!)
        return true
    }

    override fun computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            invalidate()
        }
    }

}