package com.example.racoonsquash

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathMeasure
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView

class PongActivity : AppCompatActivity(), SurfaceHolder.Callback {

    private lateinit var surfaceView: SurfaceView
    private lateinit var lineColor: Paint

    // Path-klass ritar ett "spår" från en punkt moveTo() till nästa punkt lineTo()
    // Dessa blir gränser för spel-plan
    // Verkar kunna använda PathMeasure-klassen för att detektera intersections/collisions
    private var leftBoundaryPath: Path? = null
    private var rightBoundaryPath: Path? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pong)

        surfaceView = findViewById(R.id.sv_pong)

        // Enbart för att synliggöra gränserna
        lineColor = Paint().apply {
            color = Color.CYAN
            style = Paint.Style.STROKE
            strokeWidth = 10f
        }
        surfaceView.holder.addCallback(this)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {

    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        leftBoundaryPath = createBoundaryPathLeft(width, height)
        rightBoundaryPath = createBoundaryPathRight(width, height)
        gameBounds(holder)
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {

    }

    fun gameBounds(holder: SurfaceHolder) {
        val canvas: Canvas? = holder.lockCanvas()

        // canvas?.drawColor(Color.BLACK)

        rightBoundaryPath?.let {
            canvas?.drawPath(it, lineColor)
        }

        leftBoundaryPath?.let {
            canvas?.drawPath(it, lineColor)
        }

        holder.unlockCanvasAndPost(canvas)
    }

    private fun createBoundaryPathLeft(width: Int, height: Int): Path {
        val pathLeft = Path()

        pathLeft.moveTo(0f, 0f)
        pathLeft.lineTo(0f, height.toFloat())

        return pathLeft
    }

    private fun createBoundaryPathRight(width: Int, height: Int): Path {
        val pathRight = Path()

        pathRight.moveTo(width.toFloat(), 0f)
        pathRight.lineTo(width.toFloat(), height.toFloat())

        return pathRight
    }

}