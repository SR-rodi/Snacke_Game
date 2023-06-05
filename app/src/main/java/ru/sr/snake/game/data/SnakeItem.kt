package ru.sr.snake.game.data

data class SnakeItem(
    val position: Position,
    val partBody: PartBodySnake = PartBodySnake.BODY,
) {
    companion object {
        fun createSnake(snakeSize: Int): Snake {
            val body = mutableListOf<SnakeItem>()
            for (i in 0 until snakeSize) {
                val position = Position(0, i)
                when (i) {
                    0 -> body.add(SnakeItem(position, PartBodySnake.TAIL))
                    snakeSize - 1 -> body.add(SnakeItem(position, PartBodySnake.HEAD))
                    else -> body.add(SnakeItem(position, PartBodySnake.BODY))
                }
            }
            return Snake(body)
        }
    }
}


