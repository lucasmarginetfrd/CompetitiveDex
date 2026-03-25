package com.epicjugador.competitivedex.data.network.response

import com.epicjugador.competitivedex.domain.model.PokeDetailsModel
import com.google.gson.annotations.SerializedName

data class PokeResponse (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("types") val types: List<Types>,
    @SerializedName("abilities") val abilities: List<Abilities>,
    @SerializedName("sprites") val sprites: Sprites,
    @SerializedName("stats") val stats: List<Stats>
) {
    fun toDomain(): PokeDetailsModel {
        return PokeDetailsModel(
            id = id,
            name = name,
            types = types.map { it.type.name } ,
            abilities = abilities.map { it.ability.name },
            isHidden = abilities.any { it.isHidden },
            offArtwork = sprites.other.offArtwork.frontDefault,
            effects = emptyList(),
            baseStats = stats.map { it.baseStat },
            statName = stats.map { it.stat.name }
        )
    }
}

data class Stats (
    @SerializedName("base_stat") val baseStat: Int,
    @SerializedName("stat") val stat: Stat
)

data class Stat (
    @SerializedName("name") val name: String
)

data class Types (
    @SerializedName("type") val type: Type
)

data class Type (
    @SerializedName("name") val name: String
)

data class Abilities (
    @SerializedName("ability") val ability: Ability,
    @SerializedName("is_hidden") val isHidden: Boolean
)

data class Ability (
    @SerializedName("name") val name: String
)

data class Sprites (
    @SerializedName("other") val other: Other
)

data class Other (
    @SerializedName("official-artwork") val offArtwork: OffArtwork
)

data class OffArtwork (
    @SerializedName("front_default") val frontDefault: String
)


