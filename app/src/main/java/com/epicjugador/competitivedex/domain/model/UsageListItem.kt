package com.epicjugador.competitivedex.domain.model

sealed class UsageListItem {
    data class Header (
        val title: String
    ) : UsageListItem()

    data class Entry(
        val name: String,
        val usage: Double,
        val spriteNum: Int? = null
    ) : UsageListItem()
}