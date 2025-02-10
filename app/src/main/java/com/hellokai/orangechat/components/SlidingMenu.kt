package com.hellokai.orangechat.components

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper

interface OnMenuStateChangeListener {
    fun onMenuOpen()
    fun onMenuClose()
    fun onSliding(fraction: Float)
}

class SlidingMenu(context : Context, attributeSet: AttributeSet? = null): FrameLayout(context, attributeSet) {

    private lateinit var vMenuView: View
    private lateinit var vContentView: View

    private val mViewDragHelper: ViewDragHelper = ViewDragHelper.create(this, 1.0f, object : ViewDragHelper.Callback() {
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            android.util.Log.i("SlidingMenu", "tryCaptureView: $child, width: ${child.width}, height: ${child.height}, left: ${child.left}, top: ${child.top}")
            return child == vContentView || child == vMenuView
        }

        override fun getViewHorizontalDragRange(child: View): Int {
            return vContentView.width
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            val menuWidth = vMenuView.width
            if (child == vMenuView) {
                if (left < -menuWidth) {
                    return -menuWidth
                } else if (left > 0) {
                    return 0
                } else {
                    return left
                }
            } else if (child == vContentView) {
                if (left < 0) {
                    return 0
                } else if (left > menuWidth) {
                    return menuWidth
                } else {
                    return left
                }
            }
            return 0
        }

        override fun onViewPositionChanged(changedView: View, left: Int, top: Int, dx: Int, dy: Int) {
            super.onViewPositionChanged(changedView, left, top, dx, dy)
            if (changedView == vMenuView) {
                val newLeft = vContentView.left + dx
                val right = newLeft + vContentView.width
                vContentView.layout(newLeft, top, right, bottom)
            } else if (changedView == vContentView) {
                val newLeft = vMenuView.left + dx
                vMenuView.layout(newLeft, top, left, bottom)
            }
            if (mMenuStateChangeListener != null) {
                val fraction = Math.abs(left.toFloat() / vMenuView.width)
                mMenuStateChangeListener!!.onSliding(fraction)
            }
            if ((vMenuView.left == -vMenuView.width) && isOpenMenu) {
                isOpenMenu = false
                mMenuStateChangeListener?.onMenuClose()
            } else if (vMenuView.left == 0 && !isOpenMenu) {
                isOpenMenu = true
                mMenuStateChangeListener?.onMenuOpen()
            }
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            super.onViewReleased(releasedChild, xvel, yvel)
            if (xvel < 0) {
                closeMenu()
                return
            } else if (xvel > 300) {
                openMenu()
                return
            }

            val halfMenuWidth = vMenuView.width / 2f
            if (vMenuView.left < -halfMenuWidth) {
                openMenu()
            } else {
                closeMenu()
            }
        }

    })

    private var isOpenMenu = false

    private var mMenuStateChangeListener: OnMenuStateChangeListener? = null

    override fun onFinishInflate() {
        super.onFinishInflate()
        vMenuView = getChildAt(0)
        vContentView = getChildAt(1)
        android.util.Log.i("SlidingMenu", "vMenuView: $vMenuView, vContentView: $vContentView")
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, left, top, right, bottom)
        vMenuView.layout(-vMenuView.measuredWidth, t, l, b)
        vContentView.layout(l, t, r, b)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mViewDragHelper.processTouchEvent(event!!)
        return true
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return mViewDragHelper.shouldInterceptTouchEvent(ev!!)
    }

    override fun computeScroll() {
        super.computeScroll()
        if (mViewDragHelper.continueSettling(true)) {
            invalidate()
        }
    }

    fun openMenu() {
        mViewDragHelper.smoothSlideViewTo(vMenuView, 0, vMenuView.top)
        ViewCompat.postInvalidateOnAnimation(this)
    }

    fun closeMenu() {
        mViewDragHelper.smoothSlideViewTo(vMenuView, -vMenuView.width, vMenuView.top)
        ViewCompat.postInvalidateOnAnimation(this)
    }

}