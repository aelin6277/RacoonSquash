package com.example.racoonsquash

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect

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

    fun checkBounds(bounds: Rect){
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
        if (posX-size < bounds.left || posX+size > bounds.right){
            speedX *= -1
            posX += speedX*1.2f
        }
        if(posY-size < bounds.top || posY+size > bounds.bottom){
            speedY *= -1
            posY += speedY*1.2f
        }
    }


    fun update() {
        //posY = posY + speed // bollar som aker nerat pa vanster sidan
        //posX += speed //diagonalt akande bollar
        posX += speedX

    }

    fun draw(canvas: Canvas?) {
        canvas?.drawCircle(posX, posY, size, paint)
    }

}
