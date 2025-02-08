package com.hellokai.orangechat.components

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class HScrollLoadMoreView(context: Context, attributeSet: AttributeSet? = null): ViewGroup(context, attributeSet) {

    private lateinit var mHorizontalRecyclerView: RecyclerView
    private lateinit var mMoreTextView: ShowMoreTextView

    private var rvContentWidth = 0
    private var rvContentHeight = 0
    private var showMoreViewWidth = 0
    private var showMoreViewHeight = 0
    private var mOffsetWidth = 0

    private var mConsumeMoveEvent = false
    private var mLastX = 0f
    private var mLastY = 0f
    private var mMoveIndex = 0
    private var mHintLeftMargin = 0f

    private val mReleasedAnim = ValueAnimator.ofFloat(1f, 0f).apply {
        duration = 300
        addUpdateListener {
            val value = it.animatedValue as Float
            mHorizontalRecyclerView.translationX *= value
            mMoreTextView.translationX *= value
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        mHorizontalRecyclerView = getChildAt(0) as RecyclerView
        mMoreTextView = getChildAt(1) as ShowMoreTextView

        android.util.Log.i("HScrollLoadMoreView", "onFinishInflate, mHorizontalRecyclerView:$mHorizontalRecyclerView, mMoreTextView:$mMoreTextView")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measureChild(mHorizontalRecyclerView, widthMeasureSpec, heightMeasureSpec)
        val width = mHorizontalRecyclerView.measuredWidth
        val height = mHorizontalRecyclerView.measuredHeight
        val lp = mMoreTextView.layoutParams
        mMoreTextView.measure(
            getChildMeasureSpec(widthMeasureSpec, mMoreTextView.paddingLeft + mMoreTextView.paddingRight, lp.width),
            getChildMeasureSpec(MeasureSpec.EXACTLY, mMoreTextView.paddingTop + mMoreTextView.paddingBottom, lp.height)
        )
        setMeasuredDimension(width, height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        rvContentWidth = mHorizontalRecyclerView.measuredWidth
        rvContentHeight = mHorizontalRecyclerView.measuredHeight
        showMoreViewWidth = mMoreTextView.measuredWidth
        showMoreViewHeight = rvContentHeight

        mOffsetWidth = -showMoreViewWidth
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        mHorizontalRecyclerView.layout(0, 0, rvContentWidth, rvContentHeight)
        mMoreTextView.layout(mHorizontalRecyclerView.right, 0, mHorizontalRecyclerView.right + showMoreViewWidth, showMoreViewHeight)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (mHorizontalRecyclerView == null) {
            return super.dispatchTouchEvent(ev)
        }
        when(ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                mLastX = ev.x
                mLastY = ev.y
                mHintLeftMargin = 0f
                mConsumeMoveEvent = false
            }
            MotionEvent.ACTION_MOVE -> {
                var mDeltaX = ev.x - mLastX
                val mDeltaY = ev.y - mLastY
                if (!mConsumeMoveEvent) {
                    if (Math.abs(mDeltaX) > Math.abs(mDeltaY)) {
                        parent.requestDisallowInterceptTouchEvent(true)
                    } else {
                        parent.requestDisallowInterceptTouchEvent(false)
                    }
                }
                mMoveIndex++
                if (mMoveIndex > 2) {
                    mConsumeMoveEvent = true
                }
                mLastX = ev.rawX
                mLastY = ev.rawY
                mDeltaX *= 0.5f
                android.util.Log.i("HScrollLoadMoreView", "dispatchTouchEvent, mDeltaX:$mDeltaX, mDeltaY:$mDeltaY, translationX:${mHorizontalRecyclerView.translationX}")
                if (mDeltaX > 0) {
                    if (!mHorizontalRecyclerView.canScrollHorizontally(-1) || mHorizontalRecyclerView.translationX < 0 ) {
                        var transX = mDeltaX + mHorizontalRecyclerView.translationX
                        if (mHorizontalRecyclerView.canScrollHorizontally(-1) && transX >= 0) {
                            transX = 0f
                        }
                        mHorizontalRecyclerView.translationX = transX
                        setHintTextTranslationX(mDeltaX)
                    }
                } else if (mDeltaX < 0) {
                    if (!mHorizontalRecyclerView.canScrollHorizontally(1) || mHorizontalRecyclerView.translationX > 0) {
                        var transX = mDeltaX + mHorizontalRecyclerView.translationX
                        if (mHorizontalRecyclerView.canScrollHorizontally(1) && transX <= 0) {
                            android.util.Log.i("HScrollLoadMoreView", "dispatchTouchEvent!!!!!!!!!!, transX:$transX")
                            transX = 0f
                        }
                        mHorizontalRecyclerView.translationX = transX
                        setHintTextTranslationX(mDeltaX)
                    }
                }
            }

            MotionEvent.ACTION_UP -> {
                parent.requestDisallowInterceptTouchEvent(false)
                if (!mReleasedAnim.isRunning) {
                    mReleasedAnim.start()
                }
            }
        }
        return if (mHorizontalRecyclerView.translationX != 0f) true else super.dispatchTouchEvent(ev)
    }

    fun setHintTextTranslationX(deltaX: Float) {
        var offsetX = 0
        if (mMoreTextView != null) {
            mHintLeftMargin += deltaX
            android.util.Log.i("HScrollLoadMoreView", "setHintTextTranslationX, mHintLeftMargin:$mHintLeftMargin, mOffsetWidth:$mOffsetWidth")
            if (mHintLeftMargin <= mOffsetWidth) {
                offsetX = mOffsetWidth
                mMoreTextView.setVerticalText("释放查看更多")
            } else {
                offsetX = mHintLeftMargin.toInt()
                mMoreTextView.setVerticalText("更多")
            }
            mMoreTextView.setShadowOffset(offsetX, mOffsetWidth)
            mMoreTextView.translationX = offsetX.toFloat()
        }
    }

}