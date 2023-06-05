package ru.sr.snake.game.view

import android.util.Log
import android.view.MotionEvent
import ru.sr.snake.game.data.RouteSnake
import kotlin.math.abs

class TouchEventListener {

    private var downTouchX: Float? = null
    private var downTouchY: Float? = null
    private var routeSnake: RouteSnake? = null

    fun getRouteEvent(event: MotionEvent):RouteSnake? {

        when (event.action) {
            MotionEvent.ACTION_DOWN -> onDownTouch(event.x, event.y)
            MotionEvent.ACTION_UP -> onUpTouch(event.x, event.y)
        }
        return routeSnake
    }

    private fun onDownTouch(x: Float, y: Float) {
        downTouchX = x
        downTouchY = y
    }

    private fun onUpTouch(x: Float, y: Float) {
        if (downTouchX == null && downTouchY == null) return
        val deltaX = abs(downTouchX!! - x)
        val deltaY = abs(downTouchY!! - y)
        routeSnake =
            if (deltaX > deltaY) if (downTouchX!! - x > 0) RouteSnake.LEFT else RouteSnake.RIGHT
            else if (downTouchY!! - y > 0) RouteSnake.UP else RouteSnake.BOTTOM
        Log.e("Kart",routeSnake?.name.toString())
    }

}