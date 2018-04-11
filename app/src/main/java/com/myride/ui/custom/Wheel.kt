package com.myride.ui.custom

import android.animation.ValueAnimator
import android.animation.ValueAnimator.INFINITE
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator

class Wheel(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    val paint = Paint().apply {
        isAntiAlias = true
        color = Color.WHITE
        strokeWidth = 2F
        style = Paint.Style.STROKE
    }

    init {
        ValueAnimator.ofFloat(0F, 360F)
                .apply {
                    duration = 2000
                    repeatCount = INFINITE
                    interpolator = LinearInterpolator()
                    addUpdateListener { animator ->
                        rotationValue = animator.animatedValue as Float
                        invalidate()
                    }
                }
                .start()
    }

    var rotationValue = 0F

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val center = (width / 2F)
        canvas?.let { canvas ->
            with(canvas) {
                rotate(rotationValue, center, center)
                (0..18).forEach {
                    rotate(20F, center, center)
                    drawLine(center, center, center, 0F, paint)
                }
            }
        }
    }
}