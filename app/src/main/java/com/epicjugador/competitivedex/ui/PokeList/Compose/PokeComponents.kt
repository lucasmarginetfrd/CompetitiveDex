package com.epicjugador.competitivedex.ui.PokeList.Compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.epicjugador.competitivedex.ui.PokeList.getTypeColor

@Composable
fun TypeChip(type: String) {
    Card(
        border = BorderStroke(1.dp, Color.Black),
        colors = CardDefaults.cardColors(
            containerColor = getTypeColor(type)
        )
    ) {
        Box(
            modifier = Modifier
                .width(100.dp)
                .height(30.dp),
            contentAlignment = Alignment.Center // 👈 CLAVE
        ) {
            Text(
                text = type.replaceFirstChar { it.uppercase() },
                color = Color.White,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center
            )
        }
    }
}