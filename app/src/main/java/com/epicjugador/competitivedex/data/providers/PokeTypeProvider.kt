package com.epicjugador.competitivedex.data.providers

import com.epicjugador.competitivedex.domain.model.PokeTypeInfo
import com.epicjugador.competitivedex.domain.model.PokeTypeInfo.*
import javax.inject.Inject

class PokeTypeProvider @Inject constructor() {
    fun getTypes(): List<PokeTypeInfo> {
        return listOf(
            Grass,
            Fire,
            Water,
            Bug,
            Normal,
            Poison,
            Electric,
            Ground,
            Fairy,
            Fighting,
            Psychic,
            Rock,
            Ghost,
            Ice,
            Dragon,
            Dark,
            Steel,
            Flying
        )
    }
}