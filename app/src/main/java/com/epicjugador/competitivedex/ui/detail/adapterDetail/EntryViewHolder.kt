package com.epicjugador.competitivedex.ui.detail.adapterDetail

import com.epicjugador.competitivedex.R
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.epicjugador.competitivedex.data.core.ItemSpriteManager
import com.epicjugador.competitivedex.domain.model.UsageListItem
import com.squareup.picasso.Picasso

class EntryViewHolder(
    itemView: View,
    private val spriteSize: Int,
    private val spriteColumns: Int
) : RecyclerView.ViewHolder(itemView) {

    private val icon = itemView.findViewById<ImageView>(R.id.icon)
    private val name = itemView.findViewById<TextView>(R.id.name)
    private val usage = itemView.findViewById<TextView>(R.id.usage)

    fun bind(item: UsageListItem.Entry) {

        name.text = item.name
        usage.text = String.format("%.2f%%", item.usage)

        val spriteNum = item.spriteNum

        if (spriteNum == null) {
            icon.visibility = View.GONE
            //icon.setImageDrawable(null)
            return
        } else {
            icon.visibility = View.VISIBLE
        }

        val bitmap = ItemSpriteManager.getSprite(
            spriteNum,
            spriteSize,
            spriteColumns
        )

        bitmap?.let {
            icon.setImageBitmap(it)
        }
    }
}