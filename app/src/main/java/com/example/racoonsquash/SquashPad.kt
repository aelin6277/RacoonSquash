package com.example.racoonsquash

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.MotionEvent
import android.view.View

class SquashPad(

    context: Context,
    posX: Float,
    posY: Float,
    size: Float,
    speedX: Float,
    speedY: Float,
    color: Int,
    val width: Float,
    val height:Float
) : Ball(context, posX, posY, size, speedX, speedY, color) {


    var left: Float = posX - size
        private set

    var right: Float = posX + size
        private set

    var top: Float = posY - size
        private set

    var bottom: Float = posY + size
        private set
    var drawable: Drawable? = null

    init {
        drawable = context.resources.getDrawable(R.drawable.player_pad, null)

    }

    override fun draw(canvas: Canvas?) {
        drawable?.let {
            it.setBounds(
                (posX - it.intrinsicWidth / 2).toInt(),
                (posY - it.intrinsicHeight / 2).toInt(),
                (posX + it.intrinsicWidth / 2).toInt(),
                (posY + it.intrinsicHeight / 2).toInt()
            )
            it.draw(canvas!!)
        }
    }
}