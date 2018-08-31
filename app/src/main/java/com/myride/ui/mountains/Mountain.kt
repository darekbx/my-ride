package com.myride.ui.mountains

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.myride.R

class Mountain(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    companion object {
        val SIN_RATIO = 100F
        val MULTIPER = 15F
        val RESOLUTION_STEP = 0.2F
    }

    val paint = Paint().apply {
        isAntiAlias = true
        color = context?.getColor(R.color.cyan) ?: Color.BLACK
        style = Paint.Style.FILL
    }

    init {
        ValueAnimator.ofFloat(0F, 6.28F)
                .apply {
                    duration = 700
                    repeatCount = ValueAnimator.INFINITE
                    interpolator = LinearInterpolator()
                    addUpdateListener { animator ->
                        value = animator.animatedValue as Float
                        invalidate()
                    }
                }
                .start()
    }

    var value = 0F

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let { canvas ->

            drawMoutain(canvas)
        }
    }

    private fun drawMoutain(canvas: Canvas) {
        val ratio = Math.ceil(width.toDouble() / SIN_RATIO).toFloat()
        val path = Path()
        with(path) {
            moveTo(0F, 0F)

            var i = 0.0F
            while (i < ratio) {
                val y = Math.sin(value.plus(i.toDouble())).toFloat() * MULTIPER + MULTIPER
                val x = i.times(SIN_RATIO)
                lineTo(x, y)

                i += RESOLUTION_STEP
            }

            lineTo(width.toFloat(), height.toFloat())
            lineTo(0F, height.toFloat())
            close()
        }

        canvas.drawPath(path, paint)
    }
}