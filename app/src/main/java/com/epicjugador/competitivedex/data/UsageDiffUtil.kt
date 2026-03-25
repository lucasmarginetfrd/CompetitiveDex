package com.epicjugador.competitivedex.data

import androidx.recyclerview.widget.DiffUtil
import com.epicjugador.competitivedex.domain.model.UsageListItem

class UsageDiffUtil : DiffUtil.ItemCallback<UsageListItem>() {
    override fun areItemsTheSame(
        oldItem: UsageListItem,
        newItem: UsageListItem
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: UsageListItem,
        newItem: UsageListItem
    ): Boolean {
        return oldItem == newItem
    }

}