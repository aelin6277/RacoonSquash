package com.example.racoonsquash

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView

class SquashActivity : AppCompatActivity(), SurfaceHolder.Callback {

    private lateinit var surfaceView: SurfaceView
    private lateinit var lineColor: Paint

    //Path-klass ritar ett "spår" från en punkt moveTo() till nästa punkt lineTo()
    // Dessa blir gränser för spel-plan
    // Verkar kunna använda PathMeasure-klassen för att detektera intersections/collisions
    private var gameBoundaryPath: Path? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_squash)

        surfaceView = findViewById(R.id.sv_squash)

        // Enbart för att synliggöra gränserna
        lineColor = Paint().apply {
            color = Color.MAGENTA
            style = Paint.Style.STROKE
            strokeWidth = 10f
        }

        surfaceView.holder.addCallback(this)

    }

    override fun surfaceCreated(holder: SurfaceHolder) {

    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        gameBoundaryPath = createBoundaryPath(width, height)
        drawGameBounds(holder)
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {

    }

    fun drawGameBounds(holder: SurfaceHolder) {
        val canvas: Canvas? = holder.lockCanvas()

        // canvas?.drawColor(Color.BLACK)

        gameBoundaryPath?.let {
            canvas?.drawPath(it, lineColor)
        }

        holder.unlockCanvasAndPost(canvas)
    }

    private fun createBoundaryPath(width: Int, height: Int): Path {
        val path = Path()
        path.moveTo(0f, 0f)
        path.lineTo(width.toFloat(), 0f)
        path.lineTo(width.toFloat(), height.toFloat())
        path.lineTo(0f, height.toFloat())

        return path
    }
}