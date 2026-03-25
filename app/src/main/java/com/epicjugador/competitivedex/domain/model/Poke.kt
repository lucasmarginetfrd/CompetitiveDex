package com.epicjugador.competitivedex.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Poke(
    val pokemon: List<PokeData>
) : Parcelable

@Parcelize
data class PokeData (
    val id: Int,
    val name: String,
    val offArtwork: String,
    val types: List<PokeTypes>
) : Parcelable

@Parcelize
data class PokeTypes (
    val type: PokeTyping
) : Parcelable

@Parcelize
data class PokeTyping (
    val name: String
) : Parcelable
