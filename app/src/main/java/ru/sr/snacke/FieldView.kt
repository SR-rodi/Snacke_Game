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

    private var rows: Int = DEFAULT_ROWS
    private var columns: Int = DEFAULT_COLUMNS
    private var cellSize: Int = 0
    private var cellColorOne = Color.BLUE
    private var cellColorTwo = Color.GRAY
    private var gameField: Array<Array<Cell>> = Array(columns) { Array(rows) { Cell() } }
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.FILL }

    init {
        initAttrs(attrs)
    }

    private fun initAttrs(attrs: AttributeSet?) {
        if (attrs == null) return
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.FieldView)

        cellColorOne = typedArray.getColor(R.styleable.FieldView_field_color_one, Color.BLUE)
        cellColorTwo = typedArray.getColor(R.styleable.FieldView_field_color_two, Color.GRAY)
        rows = typedArray.getInt(R.styleable.FieldView_field_rows, DEFAULT_ROWS)
        columns = typedArray.getInt(R.styleable.FieldView_field_columns, DEFAULT_COLUMNS)

        typedArray.recycle()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        cellSize = min(w / rows, h / columns)
        val paddingHorizontal = (w - (cellSize * rows)) / 2
        val paddingVertical = (h - (cellSize * columns)) / 2
        setCells(cellSize, paddingHorizontal,paddingVertical)
    }

    private fun setCells(cellSize: Int, paddingHorizontal: Int, paddingVertical: Int) {
        gameField.iterate { _, row, column ->

            val checkColor = if (row % 2 == 0) column % 2 == 0 else column % 2 != 0

            gameField[row][column] = if (checkColor)
                gameField[row][column].copy(color = cellColorTwo)
            else gameField[row][column].copy(color = cellColorOne)

            val left = (cellSize * column) + paddingHorizontal
            val top = (cellSize * row) + paddingVertical


            gameField[row][column] = gameField[row][column].copy(
                rect = Rect(
                    left, top, left + cellSize, top + cellSize
                )
            )
        }
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        gameField.iterate { cell, _, _ ->
            paint.color = cell.color
            canvas?.drawRect(cell.rect, paint)
        }
    }

    private companion object {
        const val DEFAULT_ROWS = 18
        const val DEFAULT_COLUMNS = 21
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