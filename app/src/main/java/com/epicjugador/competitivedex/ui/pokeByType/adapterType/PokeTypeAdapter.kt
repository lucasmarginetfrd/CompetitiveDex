package com.epicjugador.competitivedex.ui.pokeByType.adapterType

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.epicjugador.competitivedex.R
import com.epicjugador.competitivedex.domain.model.PokeTypeInfo

class PokeTypeAdapter(
    private var typeList: List<PokeTypeInfo> = emptyList(),
    private val onTypeSelected: (PokeTypeInfo) -> Unit
) :
    RecyclerView.Adapter<PokeTypeViewHolder>() {

    fun updateList(list: List<PokeTypeInfo>) {
        typeList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeTypeViewHolder {
        return PokeTypeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_poke_type, parent, false)
        )
    }

    override fun getItemCount() = typeList.size

    override fun onBindViewHolder(poke: PokeTypeViewHolder, position: Int) {
        poke.render(typeList[position], onTypeSelected)

    }


}