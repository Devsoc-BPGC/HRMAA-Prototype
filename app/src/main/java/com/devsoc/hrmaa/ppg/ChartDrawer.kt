package com.devsoc.hrmaa.ppg

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.view.TextureView
import java.util.concurrent.CopyOnWriteArrayList


internal class ChartDrawer(private val chartTextureView: TextureView) {
    private val paint = Paint()
    private val fillWhite = Paint()
    fun draw(data: CopyOnWriteArrayList<Measurement<Float>>) {
        val chartCanvas = chartTextureView.lockCanvas() ?: return
        chartCanvas.drawPaint(fillWhite)
        val graphPath = Path()
        val width = chartCanvas.width.toFloat()
        val height = chartCanvas.height.toFloat()
        val dataAmount = data.size
        var min = Float.MAX_VALUE
        var max = Float.MIN_VALUE
        for (dataPoint in data) {
            if (dataPoint.measurement < min) min = dataPoint.measurement
            if (dataPoint.measurement > max) max = dataPoint.measurement
        }
        graphPath.moveTo(
            0f,
            height * (data[0].measurement.minus(min)) / (max - min)
        )
        for (dotIndex in 1 until dataAmount) {
            graphPath.lineTo(
                width * dotIndex / dataAmount,
                height * (data[dotIndex].measurement.minus(min)) / (max - min)
            )
        }
        chartCanvas.drawPath(graphPath, paint)
        chartTextureView.unlockCanvasAndPost(chartCanvas)
    }

    init {
        paint.style = Paint.Style.STROKE
        paint.color = Color.BLUE
        paint.isAntiAlias = true
        paint.strokeWidth = 2f
        fillWhite.style = Paint.Style.FILL
        fillWhite.color = Color.WHITE
    }
}
