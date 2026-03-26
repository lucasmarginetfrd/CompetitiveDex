package com.epicjugador.competitivedex.ui.PokeList.adapterList

/*import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.epicjugador.competitivedex.R
import com.epicjugador.competitivedex.data.core.PokemonDiffUtil
import com.epicjugador.competitivedex.domain.model.PokeData

class PokeListAdapter(
    private val onPokeSelected: (Int) -> Unit
) : ListAdapter<PokeData, PokeListViewHolder>(PokemonDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeListViewHolder {
        return PokeListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_poke_list, parent, false)
        )
    }


    override fun onBindViewHolder(poke: PokeListViewHolder, position: Int) {
        poke.bind(getItem(position), onPokeSelected)
    }

}*/