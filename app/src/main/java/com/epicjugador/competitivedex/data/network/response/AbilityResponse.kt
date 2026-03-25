package com.epicjugador.competitivedex.data.network.response

import com.epicjugador.competitivedex.domain.model.PokeDetailsModel
import com.google.gson.annotations.SerializedName

data class AbilityResponse(
    @SerializedName("effect_entries") val effectEntries: List<Effects>
) {
    fun toDomain(): PokeDetailsModel {
        return PokeDetailsModel(
            id = 0,
            name = "",
            types = emptyList(),
            abilities = emptyList(),
            isHidden = false,
            offArtwork = "",
            effects = effectEntries
                .filter { it.language.name == "en" }
                .map { it.effect }
            ,
            baseStats = emptyList(),
            statName = emptyList()
        )
    }
}


data class Effects(
    @SerializedName("short_effect") val effect: String,
    @SerializedName("language") val language: Language
)

data class Language(
    @SerializedName("name") val name: String
)