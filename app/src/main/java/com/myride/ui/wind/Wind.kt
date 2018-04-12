package com.myride.ui.wind

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator

class Wind(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    val paint = Paint().apply {
        isAntiAlias = true
        color = Color.WHITE
        style = Paint.Style.STROKE
        strokeWidth = 4f
    }

    fun animateWind() {
        if (animating) return
        animating = true
        ValueAnimator.ofFloat(0F, width.toFloat())
                .apply {
                    duration = 1100
                    repeatCount = ValueAnimator.INFINITE
                    interpolator = LinearInterpolator()
                    addUpdateListener { animator ->
                        valueA = animator.animatedValue as Float
                        invalidate()
                    }
                }
                .start()
        ValueAnimator.ofFloat(0F, width.toFloat())
                .apply {
                    duration = 1000
                    repeatCount = ValueAnimator.INFINITE
                    interpolator = LinearInterpolator()
                    addUpdateListener { animator ->
                        valueB = animator.animatedValue as Float
                    }
                }
                .start()
        ValueAnimator.ofFloat(0F, width.toFloat())
                .apply {
                    duration = 900
                    repeatCount = ValueAnimator.INFINITE
                    interpolator = LinearInterpolator()
                    addUpdateListener { animator ->
                        valueC = animator.animatedValue as Float
                    }
                }
                .start()
    }

    var animating = false
    var valueA = 0F
    var valueB = 0F
    var valueC = 0F

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        animateWind()
        canvas?.let { canvas ->
            val xA = width - valueA
            canvas.drawLine(xA, 2F, xA + 20F, 2F, paint)

            val xB = width - valueB
            canvas.translate(0F, 8F)
            canvas.drawLine(xB, 2F, xB + 30F, 2F, paint)

            val xC = width - valueC
            canvas.translate(0F, 14F)
            canvas.drawLine(xC, 2F, xC + 26F, 2F, paint)
        }
    }
}