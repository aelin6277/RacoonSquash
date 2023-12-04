package com.example.racoonsquash

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathMeasure
import android.graphics.Rect
import android.graphics.RectF
import android.util.Log

open class Ball(
    context: Context,
    var posX: Float,
    var posY: Float,
    var size: Float,
    var speedX: Float,
    var speedY: Float,
    var color: Int){

    var paint = Paint()


    init {
        paint.color = color
    }

    fun checkBounds(posX: Float, posY: Float, size: Float, boundaryPath: Path): Boolean {
        val pathMeasure = PathMeasure(boundaryPath, false)
        val circleRect = RectF(posX - size, posY - size, posX + size, posY + size)
        //Log.d("Surface", "Circle Rect: $circleRect")
        val intersectionPath = Path()
        intersectionPath.op(boundaryPath, Path().apply {
            addOval(circleRect, Path.Direction.CW) }, Path.Op.INTERSECT)

        val intersectionMeasure = PathMeasure(intersectionPath, false)
        val length = pathMeasure.length

        val intersectionLength = intersectionMeasure.length
        Log.d("Surface", "Circle Position: ($posX, $posY)")
        Log.d("Surface", "Circle Rect: $circleRect")
        Log.d("Surface", "Boundary Path Length: ${pathMeasure.length}")
        Log.d("Surface", "Intersection Length: $intersectionLength")

        return intersectionLength > 0 && intersectionLength == length
    }

//    fun checkBounds(bounds: Rect) {
//        // Kolla vänster och höger vägg
//        if (posX - size < bounds.left || posX + size > bounds.right) {
//            speedX *= -1
//            if (posX - size < bounds.left) {
//                posX = bounds.left + size
//            } else if (posX + size > bounds.right) {
//                posX = bounds.right - size
//            }
//        }
//
//        // Kolla övre och nedre vägg
//        if (posY - size < bounds.top || posY + size > bounds.bottom) {
//            speedY *= -1
//            if (posY - size < bounds.top) {
//                posY = bounds.top + size
//            } else if (posY + size > bounds.bottom) {
//                posY = bounds.bottom - size
//            }
//        }
//    }


//    fun checkBounds(bounds: Rect){
//        if(posX-size < 0){
//            this.speedX *= -1
//            this.posX += speedX*2
//        }
//        if(posX+size > bounds.right){
//            speedX *= -1
//        }
//        if(posY-size < 0){
//            speedY *= -1
//        }
//        if(posY+size > bounds.bottom){
//            speedY *= -1
//        }
//    }
//        if (posX-size < bounds.left || posX+size > bounds.right){
//            speedX *= -1
//            posX += speedX*1.2f
//        }
//        if(posY-size < bounds.top || posY+size > bounds.bottom){
//            speedY *= -1
//            posY += speedY*1.2f
//        }
//    }


    fun update() {
        //posY = posY + speed // bollar som aker nerat pa vanster sidan
        //posX += speed //diagonalt akande bollar
        posX += speedX
        posY += speedY

    }

    open fun draw(canvas: Canvas?) {
        canvas?.drawCircle(posX, posY, size, paint)
    }

}
