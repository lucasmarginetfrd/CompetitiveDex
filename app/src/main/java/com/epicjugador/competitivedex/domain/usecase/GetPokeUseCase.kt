package com.epicjugador.competitivedex.domain.usecase

import com.epicjugador.competitivedex.domain.Repository
import javax.inject.Inject

class GetPokeUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(id: Int) = repository.getPokeDetails(id)
    suspend operator fun invoke(name: String) = repository.getAbilityDetails(name)
}