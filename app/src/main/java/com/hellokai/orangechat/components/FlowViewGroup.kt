package com.hellokai.orangechat.components

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup

class FlowViewGroup(context: Context, attrs: AttributeSet? = null) : ViewGroup(context, attrs) {

    val gap = 10
    val vGap = 10

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var desiredWidth = 0;
        var desiredHeight = 0;
        var childViewCurX = 0;

        val sizeWidth = MeasureSpec.getSize(widthMeasureSpec)
        val sizeHeight = MeasureSpec.getSize(heightMeasureSpec)

        for (i in 0 until childCount) {
            val child = getChildAt(i)

            measureChild(child, widthMeasureSpec, heightMeasureSpec)

            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight


            if (childViewCurX + childWidth > sizeWidth) {
                desiredWidth = Math.max(desiredWidth, childViewCurX) + gap
                // 换行
                childViewCurX = 0
                desiredHeight += childHeight + vGap
            } else {
                childViewCurX += childWidth
            }
        }
        setMeasuredDimension(resolveSize(desiredWidth, widthMeasureSpec), resolveSize(Math.min(desiredWidth, desiredHeight), heightMeasureSpec))
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        var childLeft = paddingLeft
        var childTop = paddingTop

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight

            if (childLeft + childWidth > measuredWidth) {
                childLeft = paddingLeft
                childTop += childHeight + vGap
            }
            android.util.Log.i("hellokaiaries", "onLayout index:$i childLeft: $childLeft, childTop: $childTop, childWidth: $childWidth, childHeight: $childHeight")
            child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight)
            childLeft += childWidth + gap
        }
    }
}