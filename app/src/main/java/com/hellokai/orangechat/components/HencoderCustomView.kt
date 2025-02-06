package com.hellokai.orangechat.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class HencoderCustomView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    val paint = Paint().apply {
        // 1. 设置画笔颜色
        color = Color.BLACK
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = 15f
        isAntiAlias = false
        // 2.文本绘制
        textSize = 150f
        textAlign = Paint.Align.LEFT
        //
        strokeCap = Paint.Cap.BUTT
    }

    val path = Path()

    init {
        val width = paint.measureText("HencoderCustomView")
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
//        canvas.drawCircle(300f, 300f, 200f, paint)
//        canvas.drawText("HencoderCustomView", 300f, 600f, paint)
//        canvas.drawRect(100f, 100f, 500f, 500f, paint)
//        canvas.drawPoint(700f, 100f, paint)

//        paint.style = Paint.Style.FILL
//        canvas.drawArc(100f, 100f, 500f, 500f, 0f, 90f, true, paint)
//        paint.style = Paint.Style.STROKE
//        canvas.drawArc(200f, 100f, 800f, 500f, 180f, 60f, false, paint)

        path.reset()
        path.addCircle(300f, 300f, 200f, Path.Direction.CW)
        canvas.drawPath(path, paint)
    }

}