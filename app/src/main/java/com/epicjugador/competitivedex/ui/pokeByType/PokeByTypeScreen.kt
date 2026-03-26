package com.epicjugador.competitivedex.ui.pokeByType

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.epicjugador.competitivedex.domain.model.PokeTypeInfo
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.items

@Composable
fun PokeByTypeScreen(
    viewModel: PokeTypeViewModel,
    onTypeSelected: (PokeTypeInfo) -> Unit
) {
    // 🔄 Reemplaza collect en lifecycleScope
    val types by viewModel.types.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp)
    ) {

        // 🔄 Reemplaza Adapter + onBindViewHolder
        items(types) { type ->

            PokeTypeItem(
                type = type,
                onClick = {
                    onTypeSelected(type)
                }
            )
        }
    }
}