package ru.sr.snacke

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

class FieldView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    View(context, attrs) {

    private val rows: Int = 10
    private val columns: Int = 21
    private var maxHeight: Int = 0
    private var maxWidth: Int = 0
    private var cellSize: Int = 0
    private var fieldColorOne = Color.BLUE
    private val fieldColorTwo = Color.GRAY
    private var gameField: Array<Array<Cell>> = Array(columns) { Array(rows) { Cell() } }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        strokeWidth = 15f
    }


    private fun setCells(cellSize: Int, paddingHorizontal: Int) {
        gameField.iterate { _, row, column ->

            val checkColor = if (row % 2 == 0) column % 2 == 0 else column % 2 != 0

            gameField[row][column] = if (checkColor)
                gameField[row][column].copy(color = fieldColorTwo)
            else gameField[row][column].copy(color = fieldColorOne)

            var left = cellSize * column
            val top = cellSize * row

            if (column == 0) left += paddingHorizontal

            gameField[row][column] = gameField[row][column].copy(
                rect = Rect(
                    left, top, left + cellSize, top + cellSize
                )
            )
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        maxHeight = h
        maxWidth = w
        cellSize = min(maxWidth / rows, maxHeight / columns)
        val padding = (cellSize * rows) / 2
        setCells(cellSize, padding)
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        gameField.iterate { cell, _, _ ->
            paint.color = cell.color
            canvas?.drawRect(cell.rect, paint)
        }
    }
}

data class Cell(
    val color: Int = Color.BLUE,
    val rect: Rect = Rect(),
)

fun Array<Array<Cell>>.iterate(action: (Cell, Int, Int) -> Unit) =
    this.forEachIndexed { row, cells ->
        cells.forEachIndexed { column, cell ->
            action(cell, row, column)
        }
    }