package com.epicjugador.competitivedex.ui.detail.Compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.epicjugador.competitivedex.data.core.ItemSpriteManager
import com.epicjugador.competitivedex.domain.model.UsageListItem

@Composable
fun UsageItem(
    item: UsageListItem.Entry,
    spritesLoaded: Boolean
) {

    val bitmap = if (spritesLoaded && item.spriteNum != null) {
        ItemSpriteManager.getSprite(item.spriteNum, 24, 16)
    } else null

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        bitmap?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 12.dp)
            )
        }

        Text(
            text = item.name,
            modifier = Modifier.weight(1f)
        )

        Text(String.format("%.2f%%", item.usage))
    }
}