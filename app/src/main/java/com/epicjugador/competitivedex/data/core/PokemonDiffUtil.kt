package com.epicjugador.competitivedex.data.core

import androidx.recyclerview.widget.DiffUtil
import com.epicjugador.competitivedex.domain.model.PokeData

class PokemonDiffUtil : DiffUtil.ItemCallback<PokeData>() {
    override fun areItemsTheSame(oldItem: PokeData, newItem: PokeData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PokeData, newItem: PokeData): Boolean {
        return oldItem == newItem
    }
}