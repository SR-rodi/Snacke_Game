package ru.sr.snake.game.view

import android.graphics.Color
import android.graphics.Paint
import android.view.SurfaceHolder
import ru.sr.snake.game.data.RouteSnake

class SnakeHolderCallback(
    rows: Int,
    columns: Int,
    snakeSize: Int,
    cellSize: Int,
    cellColorOne: Int,
    cellColorTwo: Int,
    paddingHorizontal: Int,
    paddingVertical: Int,
    private val backgroundColor: Int,
    private val snakeColor: Int,

    ) : SurfaceHolder.Callback {

    private val paintField = Paint().apply {
        color = Color.CYAN
    }
    private val paintSnake = Paint().apply {
        color = snakeColor
    }


    private val gameEngine = GameEngine(
        rows,
        columns,
        snakeSize,
        cellSize,
        cellColorOne,
        cellColorTwo,
        paddingHorizontal,
        paddingVertical
    )

    init {
        gameEngine.changeStatusGame(true)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        gameEngine.renderGame(holder, paintSnake, paintField, backgroundColor)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) = Unit

    override fun surfaceDestroyed(holder: SurfaceHolder) = Unit

    fun onChangeRouteSnake(routeSnake: RouteSnake) {
        gameEngine.changeRoute(routeSnake)
    }

}

