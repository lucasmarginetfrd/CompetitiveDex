package com.epicjugador.competitivedex.ui.detail.Compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.epicjugador.competitivedex.ui.detail.PokeDetailState

@Composable
fun PokemonHeader(
    data: PokeDetailState.Success,
    name: String,
    usage: String?
) {

    Row(modifier = Modifier.padding(16.dp)) {

        AsyncImage(
            model = data.offArtwork,
            contentDescription = null,
            modifier = Modifier.size(180.dp)
        )

        Column(
            modifier = Modifier
                .padding(start = 16.dp, top = 8.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            Text(
                text = name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            TypesRow(data.types)

            if (usage != null) {
                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Usage = $usage%",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}