package com.epicjugador.competitivedex.ui.detail.Compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TypesRow(types: List<String>) {

    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)) {

        TypeItem(types[0])

        if (types.size == 2) {
            HorizontalDivider(
                modifier = Modifier
                    .height(50.dp)
                    .width(2.dp)
                    .padding(horizontal = 20.dp)
            )
            TypeItem(types[1])
        }
    }
}