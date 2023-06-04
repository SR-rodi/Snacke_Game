package ru.sr.snacke.game.util

import ru.sr.snacke.game.data.Cell

fun Array<Array<Cell>>.iterate(action: ( Int, Int) -> Unit) =
    this.forEachIndexed { row, cells ->
        cells.forEachIndexed { column, _ ->
            action( row, column)
        }
    }


fun Array<Array<Cell>>.iterate(action: (Cell) -> Unit) =
    this.forEach { cells ->
        cells.forEach { cell ->
            action(cell)
        }
    }
