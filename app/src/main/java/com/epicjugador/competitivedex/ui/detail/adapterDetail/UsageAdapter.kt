package com.epicjugador.competitivedex.ui.detail.adapterDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.epicjugador.competitivedex.R
import com.epicjugador.competitivedex.data.UsageDiffUtil
import com.epicjugador.competitivedex.data.core.PokemonDiffUtil
import com.epicjugador.competitivedex.domain.model.UsageListItem
import com.epicjugador.competitivedex.ui.detail.adapterDetail.HeaderViewHolder

class UsageAdapter : ListAdapter<UsageListItem, RecyclerView.ViewHolder>(UsageDiffUtil()) {
    companion object {
        const val HEADER = 0
        const val ENTRY = 1
        const val SPRITE_SIZE = 24
        const val SPRITE_COLUMNS = 16
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is UsageListItem.Header -> HEADER
            is UsageListItem.Entry -> ENTRY
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            HEADER -> {
                val view = inflater.inflate(R.layout.item_usage_header, parent, false)
                HeaderViewHolder(view)
            }
            else -> {
                val view = inflater.inflate(R.layout.item_usage_entry, parent, false)
                EntryViewHolder(view, SPRITE_SIZE, SPRITE_COLUMNS)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> holder.bind(getItem(position) as UsageListItem.Header)
            is EntryViewHolder -> holder.bind(getItem(position) as UsageListItem.Entry)

        }
    }
}