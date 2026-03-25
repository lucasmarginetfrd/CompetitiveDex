package com.epicjugador.competitivedex.data

import com.epicjugador.competitivedex.data.local.database.PokemonUsageDao
import com.epicjugador.competitivedex.data.local.database.PokemonUsageEntity
import com.epicjugador.competitivedex.data.network.SmogonApiService
import com.epicjugador.competitivedex.data.network.response.PokemonUsageStats
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class RepositoryStats @Inject constructor(
    private val smogonApiService: SmogonApiService,
    private val pokemonDao: PokemonUsageDao
) {
    private var memoryCache: Map<String, PokemonUsageStats>? = null
    suspend fun getPokemonStats(
        month: String,
        format: String
    ): Map<String, PokemonUsageStats> {

        memoryCache?.let { return it }

        val cached = pokemonDao.getAll()

        if (cached.isNotEmpty()) {

            val gson = Gson()

            val itemsType = object : TypeToken<Map<String, Double>>() {}.type
            val movesType = object : TypeToken<Map<String, Double>>() {}.type
            val abilitiesType = object : TypeToken<Map<String, Double>>() {}.type
            val teammatesType = object : TypeToken<Map<String, Double>>() {}.type

            val result = cached.associate { entity ->

                val items = gson.fromJson<Map<String, Double>>(entity.itemsJson, itemsType)
                val moves = gson.fromJson<Map<String, Double>>(entity.movesJson, movesType)
                val abilities = gson.fromJson<Map<String, Double>>(entity.abilitiesJson, abilitiesType)
                val teammates = gson.fromJson<Map<String, Double>>(entity.teammatesJson, teammatesType)

                entity.name to PokemonUsageStats(
                    usage = entity.usage,
                    items = items,
                    moves = moves,
                    abilities = abilities,
                    teammates = teammates
                )
            }
            memoryCache = result
            return result
        }

        val response = smogonApiService.getStats(month, format)

        val gson = Gson()

        val entities = response.data.map { entry ->
            PokemonUsageEntity(
                name = entry.key,
                usage = entry.value.usage,
                itemsJson = gson.toJson(entry.value.items),
                movesJson = gson.toJson(entry.value.moves),
                abilitiesJson = gson.toJson(entry.value.abilities),
                teammatesJson = gson.toJson(entry.value.teammates)
            )
        }

        pokemonDao.insertAll(entities)

        memoryCache = response.data
        return response.data
    }

}