package ru.sr.snake.game.data

import android.graphics.Rect
import ru.sr.snake.game.data.Cell
import ru.sr.snake.game.util.iterate

interface GameField {
    companion object {
        fun createField(
            fieldRows: Int,
            fieldColumns: Int,
            cellSize: Int,
            paddingHorizontal: Int,
            paddingVertical: Int,
            cellColorOne: Int,
            cellColorTwo: Int,
        ): Array<Array<Cell>> {
            val gameField = Array(fieldColumns) { Array(fieldRows) { Cell() } }
            gameField.iterate { row, column ->

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
            return gameField
        }
    }
}