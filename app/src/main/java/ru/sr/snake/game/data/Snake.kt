package ru.sr.snake.game.data

data class Snake(val body: MutableList<SnakeItem>) {

    fun move(rows: Int, columns: Int, route: RouteSnake): Snake {
        moveBody()
        moveHead(route, rows, columns)
        return this
    }

    private fun moveBody() =
        body.forEachIndexed { index, snakeItem ->
            if (index != body.lastIndex) {
                body[index] = snakeItem.copy(position = body[index + 1].position)
            }
        }

    private fun moveHead(route: RouteSnake, rows: Int, columns: Int) {
        when (route) {
            RouteSnake.LEFT -> moveLeft(rows)
            RouteSnake.RIGHT -> moveRight(rows)
            RouteSnake.UP -> moveUp(columns)
            RouteSnake.BOTTOM -> moveBottom(columns)
        }
    }

    private fun moveLeft(rows: Int) {
        val head = body.last()
        val newRowPosition =
            if (head.position.row - 1 < 0) rows - 1 else head.position.row - 1
        body[body.lastIndex] =
            body.last().copy(position = Position(head.position.column, newRowPosition))
    }

    private fun moveRight(rows: Int) {
        val head = body.last()
        val newRowPosition =
            if (rows == head.position.row + 1) 0 else head.position.row + 1
        body[body.lastIndex] =
            body.last().copy(position = Position(head.position.column, newRowPosition))
    }

    private fun moveBottom(columns: Int) {
        val head = body.last()
        val newCom =
            if (columns == head.position.column + 1) 0 else head.position.column + 1
        body[body.lastIndex] =
            body.last().copy(position = Position(newCom, head.position.row))
    }

    private fun moveUp(columns: Int) {
        val head = body.last()
        val newCom =
            if (head.position.column - 1 < 0) columns - 1 else head.position.column - 1
        body[body.lastIndex] =
            body.last().copy(position = Position(newCom, head.position.row))
    }

    fun add() {}
}