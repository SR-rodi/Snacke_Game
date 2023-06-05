package ru.sr.snake.game.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.SurfaceView
import ru.sr.snake.R
import kotlin.math.min

class GameView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : SurfaceView(context, attrs) {

    private var rows: Int = DEFAULT_ROWS
    private var columns: Int = DEFAULT_COLUMNS
    private var cellColorOne = Color.BLUE
    private var cellColorTwo = Color.GRAY
    private var backgroundColor = Color.WHITE
    private var snakeColor = Color.CYAN
    private val snakeSize = 3

    init {
        initAttrs(attrs)
    }

    @SuppressLint("CustomViewStyleable")
    private fun initAttrs(attrs: AttributeSet?) {
        if (attrs == null) return
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.FieldView)

        cellColorOne = typedArray.getColor(R.styleable.FieldView_field_color_one, cellColorOne)
        cellColorTwo = typedArray.getColor(R.styleable.FieldView_field_color_two, cellColorTwo)
        backgroundColor =
            typedArray.getColor(R.styleable.FieldView_field_color_background, backgroundColor)
        snakeColor = typedArray.getColor(R.styleable.FieldView_field_color_snake, snakeColor)

        typedArray.recycle()
    }

    private fun initHolder(cellSize: Int, paddingHorizontal: Int, paddingVertical: Int) {
        val callback = SnakeHolderCallback(
            rows = rows,
            columns = columns,
            cellSize = cellSize,
            snakeSize = snakeSize,
            backgroundColor = backgroundColor,
            cellColorTwo = cellColorTwo,
            cellColorOne = cellColorOne,
            paddingHorizontal = paddingHorizontal,
            paddingVertical = paddingVertical,
            snakeColor = snakeColor
        )
        holder.addCallback(callback)

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val cellSize = min(w / rows, h / columns)
        val paddingHorizontal = (w - (cellSize * rows)) / 2
        val paddingVertical = (h - (cellSize * columns)) / 2
        initHolder(cellSize, paddingHorizontal, paddingVertical)
    }


    private companion object {
        const val DEFAULT_ROWS = 10
        const val DEFAULT_COLUMNS = 21
    }
}