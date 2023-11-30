package com.example.racoonsquash

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView

class SquashActivity : AppCompatActivity(), SurfaceHolder.Callback {

    private lateinit var surfaceView: SurfaceView
    private lateinit var lineColor: Paint

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_squash)

        surfaceView = findViewById(R.id.sv_squash)

        // Enbart för att synliggöra gränserna
        lineColor = Paint().apply {
            color = Color.MAGENTA
            strokeWidth = 20f
        }

        surfaceView.holder.addCallback(this)

    }
    override fun surfaceCreated(holder: SurfaceHolder) {
        gameBounds(holder)

    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {

    }

    fun gameBounds(holder: SurfaceHolder) {
        val canvas: Canvas? = holder.lockCanvas()
        val pointViewWidth = surfaceView.width.toFloat()
        val pointViewHeight = surfaceView.height.toFloat()


        canvas?.drawLine(0f, 0f, pointViewWidth, 0f, lineColor)
        canvas?.drawLine(pointViewWidth, 0f, pointViewWidth, pointViewHeight, lineColor)
        canvas?.drawLine(0f, pointViewHeight, pointViewWidth, pointViewHeight, lineColor)

        holder.unlockCanvasAndPost(canvas)
    }

}