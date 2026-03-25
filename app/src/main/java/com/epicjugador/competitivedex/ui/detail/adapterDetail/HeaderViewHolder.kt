package com.epicjugador.competitivedex.ui.detail.adapterDetail

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.epicjugador.competitivedex.databinding.ItemUsageHeaderBinding
import com.epicjugador.competitivedex.domain.model.UsageListItem

class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemUsageHeaderBinding.bind(view)
    fun bind(item: UsageListItem.Header) {
        binding.headerTitle.text = item.title
    }
}