package com.hellokai.orangechat.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.text.TextPaint
import android.util.AttributeSet
import android.widget.TextView
import androidx.compose.ui.unit.dp

class ShowMoreTextView(context: Context, attrs: AttributeSet? = null) : TextView(context, attrs) {

    var mDefaultText = "更多"

    val mTextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        isAntiAlias = true
        textSize = textSize
        typeface = typeface
        color = currentTextColor
    }

    val mShadowPaint = Paint().apply {
        isAntiAlias = true
        color = Color.parseColor("#4FCCCCCC")
        style = Paint.Style.FILL
        strokeWidth = 1f
    }

    val mShadowPath = Path()

    val bounds = Rect()

    var mShadowOffset = 0f

    val mCharSpacing = 4.dp

    val mNormalSpaceing = 8.dp

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val text = mDefaultText
        mTextPaint.getTextBounds(text, 0, text.length, bounds)
        var startX = layout.getLineLeft(0) + paddingLeft
        if (compoundDrawables.get(0) != null) {
            val drawRect = compoundDrawables.get(0).bounds
            startX += drawRect.right - drawRect.left
        }
        startX += compoundDrawablePadding
        var startY = baseline
        val cHeight = (bounds.bottom - bounds.top + mCharSpacing.value)
        startY -= ((text.length - 1) * cHeight / 2).toInt()
        text.forEachIndexed { i, element ->
            canvas.drawText(element.toString(), startX, startY + i * cHeight, mTextPaint)
        }

        mShadowPath.reset()
        mShadowPath.moveTo(width.toFloat(), 0f)
        mShadowPath.quadTo(mShadowOffset, height / 2f, width.toFloat(), height.toFloat())
        canvas.drawPath(mShadowPath, mShadowPaint)
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        mDefaultText = text.toString()
        super.setText(text, type)
    }

    fun setVerticalText(text: CharSequence) {
        mDefaultText = text.toString()
        invalidate()
    }

    fun setShadowOffset(offset: Int, maxOffset: Int) {
        mShadowOffset = offset.toFloat()
        val dis = maxOffset / 2 - mNormalSpaceing.value
        if (mShadowOffset > dis) {
            mShadowOffset = dis
        } else {
            mShadowOffset = dis + (offset  - dis) / 2
        }
        invalidate()
    }

}