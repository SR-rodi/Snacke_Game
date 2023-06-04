package ru.sr.snacke.game.data

import android.graphics.Color
import android.graphics.Rect

data class Cell(
    val color: Int = Color.BLUE,
    val rect: Rect = Rect(),
)