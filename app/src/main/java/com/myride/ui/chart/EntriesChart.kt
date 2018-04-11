package com.myride.ui.chart

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.myride.R
import com.myride.repository.entities.EntryTable

class EntriesChart(context: Context, val attrs: AttributeSet) : View(context, attrs) {

    companion object {
        val PADDING = 15F
        val SCALE = 30
        val DEFAULT_COLOR = Color.RED
    }

    var entries: List<EntryTable>? = null
        set(value) {
            field = value
            invalidate()
        }

    val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 5F
        applyColor()
    }

    val guidePaint = Paint().apply {
        isAntiAlias = false
        strokeWidth = 1F
        style = Paint.Style.STROKE
        color = Color.argb(230, 200, 200, 200)
        pathEffect = DashPathEffect(arrayOf(5F, 10F).toFloatArray(), 0F)
    }

    val whitePaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.WHITE
    }

    var animate = true
    var animationProgress = 0F

    private fun Paint.applyColor() {
        val attrs = context.theme.obtainStyledAttributes(
                attrs, R.styleable.EntriesChart, 0, 0)
        color = attrs.getColor(R.styleable.EntriesChart_chartColor, DEFAULT_COLOR)
        attrs.recycle()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        entries?.let { entries ->
            if (entries.size < 2) return
            animateChart()
            canvas?.let {canvas ->
                drawGuides(canvas)
                drawChart(canvas, entries)
            }
        }
    }

    fun animateChart() {
        if (animate) {
            ValueAnimator.ofFloat(0F, width.toFloat())
                    .apply {
                        duration = 1000
                        interpolator = LinearInterpolator()
                        addUpdateListener { animator ->
                            animationProgress = animator.animatedValue as Float
                            invalidate()
                        }
                    }.start()

            animate = false
        }
    }

    private fun drawAnimationMask(canvas: Canvas) {
        @Suppress("DEPRECATION")
        canvas.clipRect(animationProgress, 0F, width.toFloat(), height.toFloat(), Region.Op.DIFFERENCE)
    }

    private fun drawChart(canvas: Canvas, entries:List<EntryTable>) {
        val entriesCount = entries.size
        val chunkSize = calculateChunkSize(entriesCount)
        var left = PADDING
        var path = Path()
        var points = mutableListOf<PointF>()

        entries.forEach { entry ->
            val top = height.toFloat() - (entry.score.times(SCALE).toFloat()) + PADDING
            points.add(PointF(left, top))

            when {
                left.toInt() == PADDING.toInt() ->  path.moveTo(left, top)
                else -> path.lineTo(left, top)
            }

            left += chunkSize
        }

        drawAnimationMask(canvas)
        canvas.drawPath(path, paint)

        points.forEach { point ->
            with (point) {
                canvas.drawCircle(x, y, 6F, paint)
                canvas.drawCircle(x, y, 4F, whitePaint)
            }
        }
    }
    private fun calculateChunkSize(entriesCount: Int) =
            Math.floor((width - PADDING.times(2)).toDouble() / (entriesCount.dec()).toDouble()).toFloat()

    private fun drawGuides(canvas: Canvas) {
        (1..10).forEach { value ->
            val y = height.toFloat() - value.times(SCALE).toFloat() + PADDING
            val path = Path().apply {
                moveTo(0F, y)
                lineTo(width.toFloat(), y)
            }
            canvas.drawPath(path, guidePaint)
        }
    }
}