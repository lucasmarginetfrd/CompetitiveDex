package com.epicjugador.competitivedex.ui.detail.Compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.epicjugador.competitivedex.data.core.ItemSpriteManager
import com.epicjugador.competitivedex.ui.detail.PokeDetailState
import com.epicjugador.competitivedex.ui.detail.PokeDetailViewModel
import com.epicjugador.competitivedex.ui.detail.Utils.formatNameDetails
import com.epicjugador.competitivedex.ui.detail.Utils.formatNameSmogon

@Composable
fun PokeDetailScreen(viewModel: PokeDetailViewModel) {

    val state by viewModel.state.collectAsState()
    val stats by viewModel.pokemonStats.observeAsState()

    var currentSmogonName by remember { mutableStateOf<String?>(null) }
    var spritesLoaded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        ItemSpriteManager.loadSprites {
            spritesLoaded = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(top = 16.dp, bottom = 24.dp)
    ) {

        when (state) {

            is PokeDetailState.Loading -> {
                Text("Loading...")
            }

            is PokeDetailState.Error -> {
                Text("Error")
            }

            is PokeDetailState.Success -> {

                val data = state as PokeDetailState.Success

                val formattedName = formatNameDetails(data.name)
                currentSmogonName = formatNameSmogon(data.name)

                val pokemonStats = stats?.get(currentSmogonName)
                val usage = pokemonStats?.usage?.times(100) ?: 0.0
                val formattedUsage = String.format("%.2f", usage)

                PokemonHeader(data = data, name = formattedName, usage = formattedUsage)

                Spacer(modifier = Modifier.height(16.dp))

                StatsSection(data)

                Spacer(modifier = Modifier.height(16.dp))

                UsageSection(
                    viewModel = viewModel,
                    stats = stats,
                    smogonName = currentSmogonName,
                    abilitiesFromApi = data.abilities,
                    spritesLoaded = spritesLoaded
                )
            }

            is PokeDetailState.SuccessAbilities -> {
                // manejado dentro de AbilitySection si querés extender
            }
        }
    }
}