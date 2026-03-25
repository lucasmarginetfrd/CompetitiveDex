package com.epicjugador.competitivedex.ui.detail

sealed class PokeDetailState {
    data object Loading : PokeDetailState()
    data class Error(val message: String) : PokeDetailState()
    data class Success(
        val name: String,
        val types: List<String>,
        val abilities: List<String>,
        val isHidden: Boolean,
        val offArtwork: String,
        val baseStats: List<Int>,
        val statName: List<String>
    ) : PokeDetailState()

    data class SuccessAbilities(val effects: Map<String, String>) : PokeDetailState()
}