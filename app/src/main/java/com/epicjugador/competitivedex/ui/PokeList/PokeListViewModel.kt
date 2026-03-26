package com.epicjugador.competitivedex.ui.PokeList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.epicjugador.competitivedex.domain.model.PokeData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokeListViewModel @Inject constructor() : ViewModel() {
    private var allPokemonList: List<PokeData> = emptyList()
    private val _filteredPokemonList = MutableLiveData<List<PokeData>>()
    val filteredPokemonList: LiveData<List<PokeData>> = _filteredPokemonList

    fun loadPokemon(list: List<PokeData>) {
        allPokemonList = list
        _filteredPokemonList.value = allPokemonList
        //_filteredPokemonList.value = list
    }

    fun searchPokemon(query: String) {
        val result = if (query.isEmpty()) {
            allPokemonList
        } else {
            allPokemonList.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }
        _filteredPokemonList.value = result
    }
}