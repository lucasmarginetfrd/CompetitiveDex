package com.epicjugador.competitivedex.domain.model

import com.epicjugador.competitivedex.R

sealed class PokeTypeInfo(val id: Int, val typeName: Int, val icon: Int, val bgColor: Int) {
    data object Grass : PokeTypeInfo(1, R.string.grass, R.drawable.ic_grass, R.color.grass)
    data object Fire : PokeTypeInfo(2, R.string.fire, R.drawable.ic_fire, R.color.fire)
    data object Water : PokeTypeInfo(3, R.string.water, R.drawable.ic_water, R.color.water)
    data object Bug : PokeTypeInfo(4, R.string.bug, R.drawable.ic_bug, R.color.bug)
    data object Normal : PokeTypeInfo(5, R.string.normal, R.drawable.ic_normal, R.color.normal)
    data object Poison : PokeTypeInfo(6, R.string.poison, R.drawable.ic_poison, R.color.poison)
    data object Electric : PokeTypeInfo(7, R.string.electric, R.drawable.ic_electric, R.color.electric)
    data object Ground : PokeTypeInfo(8, R.string.ground, R.drawable.ic_ground, R.color.ground)
    data object Fairy : PokeTypeInfo(9, R.string.fairy, R.drawable.ic_fairy, R.color.fairy)
    data object Fighting : PokeTypeInfo(10, R.string.fighting, R.drawable.ic_fighting, R.color.fighting)
    data object Psychic : PokeTypeInfo(11, R.string.psychic, R.drawable.ic_psychic, R.color.psychic)
    data object Rock : PokeTypeInfo(12, R.string.rock, R.drawable.ic_rock, R.color.rock)
    data object Ghost : PokeTypeInfo(13, R.string.ghost, R.drawable.ic_ghost, R.color.ghost)
    data object Ice : PokeTypeInfo(14, R.string.ice, R.drawable.ic_ice, R.color.ice)
    data object Dragon : PokeTypeInfo(15, R.string.dragon, R.drawable.ic_dragon, R.color.dragon)
    data object Dark : PokeTypeInfo(16, R.string.dark, R.drawable.ic_dark, R.color.dark)
    data object Steel : PokeTypeInfo(17, R.string.steel, R.drawable.ic_steel, R.color.steel)
    data object Flying : PokeTypeInfo(18, R.string.flying, R.drawable.ic_flying, R.color.flying)
}