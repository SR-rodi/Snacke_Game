package ru.sr.snake.game.view

import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
import android.view.SurfaceHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.sr.snake.game.data.GameField
import ru.sr.snake.game.data.RouteSnake
import ru.sr.snake.game.data.SnakeItem
import ru.sr.snake.game.util.getCanvas
import ru.sr.snake.game.util.iterate

class GameEngine(
    private val rows: Int,
    private val columns: Int,
    snakeSize: Int,
    cellSize: Int,
    cellColorOne: Int,
    cellColorTwo: Int,
    paddingHorizontal: Int,
    paddingVertical: Int,
) {

    private var isGame: Boolean = false
    private val job = Job()
    private val scope = CoroutineScope(job + Dispatchers.IO)
    private var routeSnake = RouteSnake.BOTTOM

    private val gameField = GameField.createField(
        rows, columns, cellSize, paddingHorizontal, paddingVertical, cellColorOne, cellColorTwo
    )

    private var snake = SnakeItem.createSnake(snakeSize)

    fun changeStatusGame(isGame: Boolean) {
        this.isGame = isGame
    }

    fun changeRoute(route: RouteSnake) {
        routeSnake = route
    }

    fun renderGame(
        holder: SurfaceHolder,
        paintSnake: Paint,
        paintField: Paint,
        backgroundColor: Int,
    ) {
        scope.launch {
            while (isGame) {
                holder.getCanvas { canvas ->
                    canvas.drawColor(backgroundColor)
                    renderField(canvas, paintField)
                    renderSnake(canvas, paintSnake)
                    snake = snake.move(rows, columns, routeSnake)
                }
                delay(150)
            }
        }
    }

    private fun renderSnake(canvas: Canvas, paintSnake: Paint) =
        snake.body.forEach { snakeItem ->
            val rect =
                gameField[snakeItem.position.column][snakeItem.position.row].rect
            canvas.drawRect(rect, paintSnake)
        }


    private fun renderField(canvas: Canvas, paintField: Paint) {
        gameField.iterate { snakeItem ->
            paintField.color = snakeItem.color
            val rect = snakeItem.rect
            canvas.drawRect(rect, paintField)
        }
    }
}