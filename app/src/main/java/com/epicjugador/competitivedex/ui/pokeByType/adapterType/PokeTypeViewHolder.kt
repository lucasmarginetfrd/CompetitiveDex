package com.epicjugador.competitivedex.ui.pokeByType.adapterType

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.epicjugador.competitivedex.databinding.ItemPokeTypeBinding
import com.epicjugador.competitivedex.domain.model.PokeTypeInfo

class PokeTypeViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemPokeTypeBinding.bind(view)

    fun render(typeInfo: PokeTypeInfo, onTypeSelected: (PokeTypeInfo) -> Unit) {
        val context = binding.tvTypeName.context
        binding.tvTypeName.text = context.getString(typeInfo.typeName)
        binding.ivTypeIcon.setImageResource(typeInfo.icon)
        binding.pokeByTypeCardView.setBackgroundColor(context.getColor(typeInfo.bgColor))

        itemView.setOnClickListener {
            onTypeSelected(typeInfo)
        }
    }

}
