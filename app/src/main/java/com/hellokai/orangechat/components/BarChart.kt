package com.hellokai.orangechat.components

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewConfiguration
import android.widget.Scroller
import androidx.compose.ui.unit.dp
import kotlin.random.Random

data class BarInfo(
    val desc: String,
    val percent: Double
)

class BarChart(context: Context, attrs: AttributeSet? = null): View(context, attrs) {

    companion object {
        const val BAR_COLOR: String = "#bb434343"
    }


    private val mBarPath = Path()
    private var mCanvasWidth = 0f
    private val mBarInterval = 40f.dp
    private val mTopSpacing = 10f.dp
    private val mBottomSpacing =  10f.dp
    private val mDescTextSize = 12.dp
    private val mBarTextSpacing = 12.dp

    private var mViewWidth = 0
    private var mBarHeight = 0f
    private var mAnimRate = 0f

    private val mBarInfoList = mutableListOf<BarInfo>()
    private val mBarColor: Int = Color.parseColor(BAR_COLOR)
    private val mBarWidth = (1.25f).dp

    private val mPaint = Paint().apply {
        this.isAntiAlias = false
    }

    private var mVelocityTracker: VelocityTracker? = null
    private var mLastTouchX = 0f
    private val mMaximumVelocity = ViewConfiguration.get(context).scaledMaximumFlingVelocity.toFloat()
    private val mMinimumVelocity = ViewConfiguration.get(context).scaledMinimumFlingVelocity.toFloat()
    private val mFling = FlingRunnable(context)

    private val mAnim = ValueAnimator.ofFloat(0f, 1f).apply {
        duration = 1000
        addUpdateListener {
            mAnimRate = it.animatedValue as Float
            postInvalidate()
        }
    }

    init {
        isClickable = true
    }

    fun testSetBarInfoList() {
        fun createBarInfo(): List<BarInfo> {
            val data: MutableList<BarInfo> = ArrayList<BarInfo>()
            val random = Random(System.currentTimeMillis())
            for (i in 1..100) {
                data.add(BarInfo(i.toString() + "日", random.nextDouble()))
            }

            return data
        }
        setBarInfoList(createBarInfo())
    }

    fun setBarInfoList(barInfoList: List<BarInfo>) {
        this.mBarInfoList.clear()
        this.mBarInfoList.addAll(barInfoList)

        this.mCanvasWidth = (this.mBarInfoList.size + 1f) * mBarInterval.value

        mFling.stop()

        if (mAnim.isRunning) {
            mAnim.cancel()
        }

        mAnimRate = 0f

        scrollTo(0, 0)
        postInvalidate()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (mCanvasWidth < mViewWidth) {
            return true
        }
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain()
        }
        mVelocityTracker?.addMovement(event)
        if (MotionEvent.ACTION_DOWN == event?.action) {
            mLastTouchX = event.x
            mFling.stop()
        } else if (MotionEvent.ACTION_MOVE == event?.action) {
            val scrollLengthX = event.x - mLastTouchX
            val endX = scrollX - scrollLengthX
            log("onTouchEvent -> move -> scrollLengthX: $scrollLengthX -> endX: $endX, scrollX: $scrollX")
            if (scrollLengthX > 0) { // 画布向右移动
                if (endX <= 0) {
                    scrollTo(0, 0)
                } else {
                    scrollBy(-scrollLengthX.toInt(), 0)
                }
            } else if (scrollLengthX < 0) { // 画布向左移动
                if (endX >= mCanvasWidth - mViewWidth) {
                    scrollTo((mCanvasWidth - mViewWidth).toInt(), 0)
                } else {
                    scrollBy(-scrollLengthX.toInt(), 0)
                }
            }
            mLastTouchX = event.x
        } else if (MotionEvent.ACTION_UP == event?.action) {
            mVelocityTracker?.computeCurrentVelocity(1000, mMaximumVelocity)
            val velocityX = mVelocityTracker!!.xVelocity
            // 滑动速度大于最小速度
            if (Math.abs(velocityX) > mMinimumVelocity) {
                val initX = scrollX
                val maxX = (mCanvasWidth - mViewWidth).toInt()
                if (maxX > 0) {
                    mFling.start(initX, velocityX.toInt(), initX, maxX)
                }
            }
            if (mVelocityTracker != null) {
                mVelocityTracker?.recycle()
                mVelocityTracker = null
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.mBarHeight = h - mTopSpacing.value - mBottomSpacing.value - mDescTextSize.value - mBarTextSpacing.value
        this.mViewWidth = w
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawBar(canvas)
        drawDot(canvas)
    }

    private fun drawBar(canvas: Canvas) {
        mBarPath.reset()
        mBarInfoList.forEachIndexed { index, barInfo ->
            val x = (index + 1) * mBarInterval.value
            if (isInvisibleArea(x)) {
                log("drawBar -> isInvisibleArea -> index: $index -> x: $x")
                mBarPath.moveTo(x, mTopSpacing.value)
                mBarPath.lineTo(x, mBarHeight + mTopSpacing.value)
            }
        }
        mPaint.color = mBarColor
        mPaint.strokeWidth = mBarWidth.value
        mPaint.style = Paint.Style.STROKE
        canvas.drawPath(mBarPath, mPaint)
    }

    private fun drawDot(canvas: Canvas) {
        mPaint.style = Paint.Style.FILL
        mBarInfoList.forEachIndexed { index, barInfo ->
            val x = (index + 1) * mBarInterval.value
            if (isInvisibleArea(x)) {
                val curBarDotY = mBarHeight * (1 - barInfo.percent * mAnimRate).toFloat() + mTopSpacing.value
                mPaint.color = Color.RED
                canvas.drawCircle(x, curBarDotY, 5f, mPaint)
            }
        }
    }

    private fun isInvisibleArea(x: Float): Boolean {
        val dx = x - scrollX
        return (-mBarInterval.value <= dx) && (dx <= mViewWidth + mBarInterval.value)
    }

    private fun log(msg: String) {
        android.util.Log.i("barchart", msg)

    }

    inner class FlingRunnable(context: Context) : Runnable {

        val mScroller = Scroller(context, null , false)

        var mInitX =0
        var mMinX = 0
        var mMaxX = 0
        var mVelocityX = 0

        fun start(initX: Int, velocityX: Int, minX: Int, maxX: Int) {
            this.mInitX = initX
            this.mVelocityX = velocityX
            this.mMinX = minX
            this.mMaxX = maxX
            if (!mScroller.isFinished) {
                mScroller.abortAnimation()
            }
            log("FlingRunnable -> start -> initX: $initX -> velocityX: $velocityX -> minX: $minX -> maxX: $maxX")
            mScroller.fling(initX, 0, velocityX, 0, 0, maxX, 0, 0)
            post(this)
        }

        override fun run() {
            if (!mScroller.computeScrollOffset()) {
                mAnimRate = 0f;
                if (mAnim.isRunning) {
                    mAnim.cancel()
                }
                mAnim.start()
                return
            }
            val currX = mScroller.currX
            var diffX = mInitX - currX
            log("FlingRunnable -> run -> currX: $currX -> diffX: $diffX")
            var isEnd = false

            if (diffX != 0) {
                // 超出右边界
                if (scrollX + diffX >= mCanvasWidth - mViewWidth) {
                    diffX = (mCanvasWidth - mViewWidth).toInt() - scrollX
                    isEnd = true
                }
                // 超出左边界
                if (scrollX <= 0) {
                    diffX = -scrollX
                    isEnd = true
                }
                if (!mScroller.isFinished) {
                    scrollBy(diffX, 0)
                }
                mInitX = currX
            }

            if (!isEnd) {
                post(this)
            }
        }

        fun stop() {
            if (!mScroller.isFinished) {
                mScroller.abortAnimation()
            }
        }
    }
}