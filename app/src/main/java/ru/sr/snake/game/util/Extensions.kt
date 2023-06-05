package ru.sr.snake.game.util

import android.graphics.Canvas
import android.view.SurfaceHolder
import ru.sr.snake.game.data.Cell

fun Array<Array<Cell>>.iterate(action: (Int, Int) -> Unit) =
    this.forEachIndexed { row, cells ->
        cells.forEachIndexed { column, _ ->
            action(row, column)
        }
    }


fun Array<Array<Cell>>.iterate(action: (Cell) -> Unit) =
    this.forEach { cells ->
        cells.forEach { cell ->
            action(cell)
        }
    }

suspend fun SurfaceHolder.getCanvas(draw: (Canvas) -> Unit) {
    val canvas = this.lockCanvas()
    draw(canvas)
    this.unlockCanvasAndPost(canvas)
}