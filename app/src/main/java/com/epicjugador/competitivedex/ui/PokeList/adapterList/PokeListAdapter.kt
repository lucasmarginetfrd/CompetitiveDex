package com.epicjugador.competitivedex.ui.PokeList.adapterList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.epicjugador.competitivedex.R
import com.epicjugador.competitivedex.data.core.PokemonDiffUtil
import com.epicjugador.competitivedex.domain.model.PokeData

class PokeListAdapter(
    //private var pokeList: List<PokeData>,
    private val onPokeSelected: (Int) -> Unit
) : ListAdapter<PokeData, PokeListViewHolder>(PokemonDiffUtil()) {
// RecyclerView.Adapter<PokeListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeListViewHolder {
        return PokeListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_poke_list, parent, false)
        )
    }

    //override fun getItemCount() = pokeList.size

    override fun onBindViewHolder(poke: PokeListViewHolder, position: Int) {
        poke.bind(getItem(position), onPokeSelected)
    }

    /*fun updateList(newList: List<PokeData>) {
        pokeList = newList
        notifyDataSetChanged()
    }*/
}