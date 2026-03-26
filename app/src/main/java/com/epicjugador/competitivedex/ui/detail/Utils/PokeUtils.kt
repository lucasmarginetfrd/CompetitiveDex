package com.epicjugador.competitivedex.ui.detail.Utils

import kotlin.math.round

fun formatNameDetails(name: String): String {
    val parts = name.lowercase().split("-").toMutableList()

    val hasMega = parts.remove("mega")

    val formattedParts = parts.map { part ->
        part.replaceFirstChar { it.uppercase() }
    }.toMutableList()

    if (hasMega) {
        formattedParts.add(0, "Mega")
    }

    return formattedParts.joinToString(" ")
}

fun formatNameSmogon(name: String): String {
    val parts = name.lowercase().split("-").toMutableList()
    val hasMega = parts.remove("mega")
    parts.remove("breed")
    parts.remove("mask")
    parts.remove("incarnate")

    val prefixParadox = setOf(
        "flutter",
        "great",
        "roaring",
        "scream",
        "brute",
        "sandy",
        "slither",
        "walking",
        "raging",
        "gouging"
    )
    val pastParadox = parts.firstOrNull() in prefixParadox
    val futureParadox = parts.firstOrNull() == "iron"

    val formattedParts = parts.map { part ->
        part.replaceFirstChar { it.uppercase() }
    }.toMutableList()
    if (hasMega) {
        formattedParts.add(0, "Mega")
    }
    if (pastParadox || futureParadox) {
        return formattedParts.joinToString(" ")
    }

    return formattedParts.joinToString("-")

}