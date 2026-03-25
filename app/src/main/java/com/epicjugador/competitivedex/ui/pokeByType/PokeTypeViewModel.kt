package com.epicjugador.competitivedex.ui.pokeByType

import androidx.lifecycle.ViewModel
import com.epicjugador.competitivedex.data.providers.PokeTypeProvider
import com.epicjugador.competitivedex.domain.model.PokeTypeInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PokeTypeViewModel @Inject constructor(pokeTypeProvider: PokeTypeProvider) : ViewModel() {

    private var _types = MutableStateFlow<List<PokeTypeInfo>>(emptyList())
    val types: StateFlow<List<PokeTypeInfo>> = _types

    init {
        _types.value = pokeTypeProvider.getTypes()
    }
}
