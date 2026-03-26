package com.epicjugador.competitivedex.ui.detail.Compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.epicjugador.competitivedex.ui.detail.PokeDetailState

@Composable
fun StatsSection(data: PokeDetailState.Success) {

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {

        Spacer(modifier = Modifier.height(8.dp))

        StatsChart(
            data = mapOf(
                data.statName[0].uppercase() to data.baseStats[0],
                data.statName[1] to data.baseStats[1],
                data.statName[2] to data.baseStats[2],
                "Sp.Atk." to data.baseStats[3],
                "Sp.Def" to data.baseStats[4],
                data.statName[5] to data.baseStats[5],
            ),
            maxValue = 255
        )
    }
}