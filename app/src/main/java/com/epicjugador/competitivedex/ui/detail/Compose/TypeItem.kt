package com.epicjugador.competitivedex.ui.detail.Compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.epicjugador.competitivedex.R

@Composable
fun TypeItem(type: String) {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Image(
            painter = painterResource(id = getTypeIcon(type)),
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(type.replaceFirstChar { it.uppercase() })
    }
}

fun getTypeIcon(type: String): Int {
    return when (type) {
        "grass" -> R.drawable.ic_grass
        "fire" -> R.drawable.ic_fire
        "water" -> R.drawable.ic_water
        "bug" -> R.drawable.ic_bug
        "normal" -> R.drawable.ic_normal
        "poison" -> R.drawable.ic_poison
        "electric" -> R.drawable.ic_electric
        "ground" -> R.drawable.ic_ground
        "fairy" -> R.drawable.ic_fairy
        "fighting" -> R.drawable.ic_fighting
        "psychic" -> R.drawable.ic_psychic
        "rock" -> R.drawable.ic_rock
        "ghost" -> R.drawable.ic_ghost
        "ice" -> R.drawable.ic_ice
        "dragon" -> R.drawable.ic_dragon
        "dark" -> R.drawable.ic_dark
        "steel" -> R.drawable.ic_steel
        "flying" -> R.drawable.ic_flying
        else -> R.drawable.ic_normal
    }
}