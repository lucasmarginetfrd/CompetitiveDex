package com.epicjugador.competitivedex.data.network.response

import com.epicjugador.competitivedex.data.network.response.PokemonUsageStats

data class AllStatsResponse(
    val info: Map<String, Any>,
    val data: Map<String, PokemonUsageStats>
)