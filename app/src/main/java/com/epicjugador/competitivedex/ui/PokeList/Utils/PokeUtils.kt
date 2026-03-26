package com.epicjugador.competitivedex.ui.PokeList

import androidx.compose.ui.graphics.Color

fun formatName(name: String): String {
    return name
        .lowercase()
        .split("-")
        .filter { it != "mega" }
        .map { it.replaceFirstChar(Char::uppercase) }
        .let {
            if (name.contains("-mega")) listOf("Mega") + it else it
        }
        .joinToString(" ")
}

fun getTypeColor(type: String): Color {
    return when (type.lowercase()) {
        "grass" -> Color(0xFF3FA129)
        "fire" -> Color(0xFFE62829)
        "water" -> Color(0xFF2980EF)
        "bug" -> Color(0xFF91A119)
        "normal" -> Color(0xFF9FA19F)
        "poison" -> Color(0xFF8F41CB)
        "electric" -> Color(0xFFFAC000)
        "ground" -> Color(0xFF915121)
        "fairy" -> Color(0xFFEF71EF)
        "fighting" -> Color(0xFFFF8000)
        "psychic" -> Color(0xFFEF4179)
        "rock" -> Color(0xFFAFA981)
        "ghost" -> Color(0xFF704170)
        "ice" -> Color(0xFF3FD8FF)
        "dragon" -> Color(0xFF3F51B5)
        "dark" -> Color(0xFF50413F)
        "steel" -> Color(0xFF60A1B8)
        "flying" -> Color(0xFF81B9EF)
        else -> Color.White
    }
}

fun lightenColor(color: Color, factor: Float): Color {
    return Color(
        red = color.red + (1f - color.red) * factor,
        green = color.green + (1f - color.green) * factor,
        blue = color.blue + (1f - color.blue) * factor,
        alpha = 1f
    )
}