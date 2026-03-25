package com.epicjugador.competitivedex.domain.model

data class PokeDetailsModel (
    val id: Int,
    val name: String,
    val types: List<String>,
    val abilities: List<String>,
    val isHidden: Boolean,
    val offArtwork: String,
    val effects: List<String>,
    val baseStats: List<Int>,
    val statName: List<String>
)