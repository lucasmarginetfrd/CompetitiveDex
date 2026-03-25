package com.epicjugador.competitivedex.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epicjugador.competitivedex.data.RepositoryImp
import com.epicjugador.competitivedex.data.RepositoryStats
import com.epicjugador.competitivedex.data.network.response.PokemonUsageStats
import com.epicjugador.competitivedex.domain.usecase.GetPokeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PokeDetailViewModel @Inject constructor(
    private val getPokeUseCase: GetPokeUseCase,
    private val repository: RepositoryStats,
    private val repositoryApi: RepositoryImp
) :
    ViewModel() {
    private val _state = MutableStateFlow<PokeDetailState>(PokeDetailState.Loading)
    val state: StateFlow<PokeDetailState> = _state

    //private val _effectList = mutableListOf<String>()
    private val _effectMap = mutableMapOf<String, String>()
    private val _pokemonStats = MutableLiveData<Map<String, PokemonUsageStats>>()
    val pokemonStats: LiveData<Map<String, PokemonUsageStats>> = _pokemonStats

    fun getItemSpriteNum(itemName: String): Int? = repositoryApi.getItemSpriteNum(itemName)
    fun getItemDisplayName(itemName: String): String = repositoryApi.getItemDisplayName(itemName)
    fun getMoveName(id: String): String = repositoryApi.getMoveName(id)
    fun getAbilityName(id: String): String = repositoryApi.getAbilityName(id)


    fun loadStats() {
        viewModelScope.launch {

            repositoryApi.loadShowdownData()

            val result = repository.getPokemonStats(
                "2026-02",
                "gen9vgc2026regf"
            )
            _pokemonStats.value = result
        }
    }

    fun getPokeDetails(id: Int) {
        viewModelScope.launch {
            _state.value = PokeDetailState.Loading
            val result = withContext(Dispatchers.IO) {
                getPokeUseCase(id)
            }
            if (result != null) {
                _state.value = PokeDetailState.Success(
                    result.name,
                    result.types,
                    result.abilities,
                    result.isHidden,
                    result.offArtwork,
                    result.baseStats,
                    result.statName
                )

            } else {
                _state.value = PokeDetailState.Error("Error")
            }
        }
    }

    fun getAbilityDetails(name: String) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                getPokeUseCase(name)
            }
            if (result != null) {
                /*_effectList.addAll(result.effects)
                _state.value = PokeDetailState.SuccessAbilities(
                    _effectList.toList()
                )*/
                _effectMap[name] = result.effects.firstOrNull() ?: ""
                _state.value = PokeDetailState.SuccessAbilities(
                    effects = _effectMap.toMap()
                )
            } else {
                _state.value = PokeDetailState.Error("Error")
            }
        }

    }

    fun loadAbilities(abilities: List<String>) {
        viewModelScope.launch {
            val results = abilities.map { ability ->
                async(Dispatchers.IO) { getPokeUseCase(ability) }
            }.awaitAll()

            results.forEachIndexed { index, result ->
                result?.let {
                    _effectMap[abilities[index]] = it.effects.firstOrNull() ?: ""
                }
            }

            _state.value = PokeDetailState.SuccessAbilities(
                effects = _effectMap.toMap()
            )
        }
    }

}