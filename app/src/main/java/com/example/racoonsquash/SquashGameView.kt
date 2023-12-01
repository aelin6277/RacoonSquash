package com.example.racoonsquash

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.graphics.Typeface
import android.icu.text.Transliterator
import android.view.MotionEvent
import android.widget.TextView

class SquashGameView(context: Context) : SurfaceView(context), SurfaceHolder.Callback, Runnable {
    private var thread: Thread? = null
    private var running = false
    lateinit var canvas: Canvas
    lateinit var ball1: Ball
    lateinit var posX: Transliterator.Position
    lateinit var posY: Transliterator.Position
    private var lineColor: Paint
    private var textPaint: Paint
    private var score: Int = 0;

    //Path-klass ritar ett "spår" från en punkt moveTo() till nästa punkt lineTo()
    // Dessa blir gränser för spel-plan
    // Verkar kunna använda PathMeasure-klassen för att detektera intersections/collisions
    private var gameBoundaryPath: Path? = null

    var bounds = Rect() //for att kunna studsa m vaggarna
    var mHolder: SurfaceHolder? = holder

    init {
        if (mHolder != null) {
            mHolder?.addCallback(this)
        }

        // Score-text-attribut
        textPaint = Paint().apply {
            color = Color.GREEN
            alpha = 200
            textSize = 60.0F
            typeface = Typeface.create("serif-monospace", Typeface.BOLD)
        }
        // Enbart för att synliggöra gränserna
        lineColor = Paint().apply {
            color = Color.MAGENTA
            style = Paint.Style.STROKE
            strokeWidth = 10f
        }
        setup()
    }

    private fun setup() {
        ball1 = Ball(this.context, 100f, 100f, 30f, 7f, 7f, Color.RED)

//    private fun setup() {
//        ball1 = Ball(this.context)
//        ball1.posX = 100f
//        ball1.posY = 100f
//        ball1.paint.color = Color.RED
    }

    fun start() {
        running = true
        thread =
            Thread(this) //en trad har en konstruktor som tar in en runnable, vilket sker i denna klass se rad 10
        thread?.start()
    }

    fun stop() {
        running = false
        try {
            thread?.join() //join betyder att huvudtraden komemr vanta in att traden dor ut av sig sjalv
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    fun update() {
        ball1.update()

        // Räknar bara när boll rör långsidan just nu
        if (ball1.posX >= width - ball1.size) {
            updateScore()
        }
    }

    //med denna kod kan jag rora pa boll2 som star stilla annars
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val vX =
            event?.x.toString() //ett satt att hantera att en vill ha null och en nonnull versioner av datatyperna
        val vY = event?.y.toString()

        return true
        // return super.onTouchEvent(event)
    }

//    fun draw() {
//        canvas = holder!!.lockCanvas()
//        canvas.drawColor(Color.BLUE)
//
//
//        holder!!.unlockCanvasAndPost(canvas)
//    }

    //dessa startar och stoppar min thread:
    override fun surfaceCreated(holder: SurfaceHolder) {
        // start()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        bounds = Rect(0, 0, width, height)
        start()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        stop()
    }

    //run/metoden ar en metod som vi fick fran interface Runnable och ar kopplat till dess Thread.Run anropas nar vi kor Thread.start()
    //den kor en while loop med var running variable pch anropar update och draw:
    override fun run() {
        while (running) {
            update()
            drawGameBounds(holder)
            gameBoundaryPath = createBoundaryPath(width, height)
            ball1.checkBounds(bounds)
        }
    }
    fun drawGameBounds(holder: SurfaceHolder) {
        val canvas: Canvas? = holder.lockCanvas()
        canvas?.drawColor(Color.BLACK)

        gameBoundaryPath?.let {
            canvas?.drawPath(it, lineColor)
        }

        // Placera text
        canvas?.drawText("Score: $score", canvas.width.toFloat() - 400, 0f + 100, textPaint)

        ball1.draw(canvas)
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
    private fun updateScore(): Int {
        score++
        return score
    }
}