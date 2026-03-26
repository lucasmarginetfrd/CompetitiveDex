package com.epicjugador.competitivedex.ui.detail.Compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epicjugador.competitivedex.domain.model.UsageListItem

@Composable
fun UsageList(
    title: String,
    list: List<UsageListItem.Entry>,
    spritesLoaded: Boolean
) {

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {

        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(8.dp)
        )

        list.forEach {
            UsageItem(it, spritesLoaded)
        }
    }
}