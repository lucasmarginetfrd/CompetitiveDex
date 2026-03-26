package com.epicjugador.competitivedex.ui.detail.Compose

import androidx.compose.runtime.Composable
import com.epicjugador.competitivedex.data.network.response.PokemonUsageStats
import com.epicjugador.competitivedex.domain.model.UsageListItem
import com.epicjugador.competitivedex.ui.detail.PokeDetailViewModel

@Composable
fun UsageSection(
    viewModel: PokeDetailViewModel,
    stats: Map<String, PokemonUsageStats>?,
    smogonName: String?,
    abilitiesFromApi: List<String>, // 🔥 NUEVO
    spritesLoaded: Boolean
) {

    val pokemon = stats?.get(smogonName)

    val abilitiesMap = pokemon?.abilities ?: emptyMap()
    val itemsMap = pokemon?.items ?: emptyMap()
    val movesMap = pokemon?.moves ?: emptyMap()

    val totalUsage = abilitiesMap.values.sum().takeIf { it > 0 } ?: 1.0

    val itemsList = if (itemsMap.isEmpty()) {
        listOf(
            UsageListItem.Entry(
                name = "NINGUNO",
                usage = 100.0,
                spriteNum = 0
            )
        )
    } else {
        itemsMap.toList()
            .sortedByDescending { it.second }
            .take(6)
            .map {
                UsageListItem.Entry(
                    viewModel.getItemDisplayName(it.first),
                    (it.second / totalUsage) * 100,
                    viewModel.getItemSpriteNum(it.first)
                )
            }
    }

    UsageList(
        title = "Items",
        list = itemsList,
        spritesLoaded = spritesLoaded
    )

    val abilitiesList = if (abilitiesMap.isEmpty()) {
        abilitiesFromApi.map {
            UsageListItem.Entry(
                name = viewModel.getAbilityName(it),
                usage = 0.0
            )
        }
    } else {
        abilitiesMap.toList()
            .sortedByDescending { it.second }
            .take(3)
            .map {
                UsageListItem.Entry(
                    viewModel.getAbilityName(it.first),
                    (it.second / totalUsage) * 100
                )
            }
    }

    UsageList(
        title = "Abilities",
        list = abilitiesList,
        spritesLoaded = spritesLoaded
    )

    val movesList = if (movesMap.isEmpty()) {
        listOf(
            UsageListItem.Entry(
                name = "No disponibles",
                usage = 100.0
            )
        )
    } else {
        movesMap.toList()
            .sortedByDescending { it.second }
            .take(6)
            .map {
                UsageListItem.Entry(
                    viewModel.getMoveName(it.first),
                    (it.second / totalUsage) * 100
                )
            }
    }

    UsageList(
        title = "Moves",
        list = movesList,
        spritesLoaded = spritesLoaded
    )
}