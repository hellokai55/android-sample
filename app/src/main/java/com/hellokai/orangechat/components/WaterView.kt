package com.hellokai.orangechat.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.util.Timer
import java.util.TimerTask

class WaterView(context: Context, attrs: AttributeSet? = null): View(context, attrs) {

    val paint = Paint().apply {
        isAntiAlias = false
        color = Color.BLUE
    }

    var mFirstWaterLine = floatArrayOf()
    var mSecondWaterLine = floatArrayOf()
    var mWaveMoveValue = 0f

    init {
        moveWaterLine()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        android.util.Log.d("WaterView", "onSizeChanged w: $w")
        mFirstWaterLine = FloatArray(w)
        mSecondWaterLine =  FloatArray(w)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawWaterWave(canvas)
    }

    var mWaveTimer: Timer? = null

    fun moveWaterLine() {
        mWaveTimer = Timer()
        mWaveTimer?.schedule(object : TimerTask() {
            override fun run() {
                mWaveMoveValue += 1f
                if (mWaveMoveValue == 100f) {
                    mWaveMoveValue = 1f
                }
                postInvalidate()
            }
        }, 500, 200)
    }

    fun drawWaterWave(canvas: Canvas) {
        val len = width.toFloat()
        android.util.Log.d("WaterView", "len: $len")
        val cycleFactorW = 2 * Math.PI / len

        canvas.save()
        for (i in 0 until len.toInt() - 1) {
            mFirstWaterLine[i] = (50 * Math.sin(cycleFactorW * i + mWaveMoveValue)).toFloat()
            mSecondWaterLine[i] = (75 * Math.sin(cycleFactorW * i + mWaveMoveValue)).toFloat()
//            canvas.drawLine(i.toFloat(), mFirstWaterLine[i], i.toFloat(), len, paint)
//            canvas.drawLine(i.toFloat(), mSecondWaterLine[i], i.toFloat(), len, paint)
            canvas.drawPoint(i.toFloat(), mFirstWaterLine[i], paint)
        }
        canvas.restore()
    }

}