package com.epicjugador.competitivedex.data.network.response

import com.google.gson.annotations.SerializedName

data class PokemonUsageStats(
    @SerializedName("usage") val usage: Double,
    @SerializedName("Moves") val moves: Map<String, Double>?,
    @SerializedName("Items") val items: Map<String, Double>?,
    @SerializedName("Abilities") val abilities: Map<String, Double>?,
    @SerializedName("Teammates") val teammates: Map<String, Double>?,
    //@SerializedName("Spreads") val spreads: Map<String, Double>?
)