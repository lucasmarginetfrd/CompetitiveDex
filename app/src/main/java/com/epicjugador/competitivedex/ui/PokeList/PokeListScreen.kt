package com.epicjugador.competitivedex.ui.PokeList

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.livedata.observeAsState
import com.epicjugador.competitivedex.domain.model.PokeData
import com.epicjugador.competitivedex.ui.PokeList.adapterList.PokeListViewModel

@Composable
fun PokeListScreen(
    viewModel: PokeListViewModel,
    onPokemonClick: (Int) -> Unit
) {
    // 👇 IMPORTANTE: tipo explícito para evitar errores
    val pokemonList: List<PokeData> by viewModel
        .filteredPokemonList
        .observeAsState(emptyList())

    var query by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {

        OutlinedTextField(
            value = query,
            shape = RoundedCornerShape(24.dp),
            onValueChange = {
                query = it
                viewModel.searchPokemon(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            placeholder = { Text("Search Pokemon") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(
                items = pokemonList,
                key = { pokemon -> pokemon.id } // 👈 ahora sí funciona
            ) { pokemon ->

                PokeItem(
                    poke = pokemon,
                    onClick = {
                        onPokemonClick(pokemon.id)
                    }
                )
            }
        }
    }
}