package com.epicjugador.competitivedex.domain

import com.epicjugador.competitivedex.domain.model.PokeDetailsModel

interface Repository {
    suspend fun getPokeDetails(id: Int): PokeDetailsModel?
    suspend fun getAbilityDetails(name: String): PokeDetailsModel?
}