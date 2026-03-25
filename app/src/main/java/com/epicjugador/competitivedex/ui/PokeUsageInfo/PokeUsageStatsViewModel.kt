package com.epicjugador.competitivedex.ui.PokeUsageInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epicjugador.competitivedex.data.RepositoryStats
import com.epicjugador.competitivedex.data.network.response.PokemonUsageStats
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokeUsageStatsViewModel @Inject constructor(
    private val repository: RepositoryStats
) : ViewModel() {
    private val _pokemonStats = MutableLiveData<Map<String, PokemonUsageStats>>()
    val pokemonStats: LiveData<Map<String, PokemonUsageStats>> = _pokemonStats

    fun loadStats() {
        viewModelScope.launch {
            val result = repository.getPokemonStats(
                "2026-02",
                "gen9vgc2026regf"
            )
            _pokemonStats.value = result
        }
    }
}