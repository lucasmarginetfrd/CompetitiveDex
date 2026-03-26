package com.epicjugador.competitivedex.ui.PokeList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import coil.compose.AsyncImage
import com.epicjugador.competitivedex.domain.model.PokeData

@Composable
fun PokeItem(
    poke: PokeData,
    onClick: () -> Unit
) {
    val type1 = poke.types[0].type.name
    val type2 = poke.types.getOrNull(1)?.type?.name

    val color1 = getTypeColor(type1)
    val color2 = type2?.let { getTypeColor(it) } ?: lightenColor(color1, 0.7f)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(horizontal = 10.dp, vertical = 4.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(listOf(color1, color2))
                )
        ) {
            Row(modifier = Modifier.fillMaxSize()) {

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(vertical = 10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = formatName(poke.name),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            shadow = Shadow(
                                color = Color.Black,
                                offset = Offset(1f, 1f),
                                blurRadius = 15f
                            )
                        )
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        TypeChip(type1)

                        Spacer(modifier = Modifier.width(10.dp))

                        if (type2 != null) {
                            TypeChip(type2)
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .width(90.dp)
                        .fillMaxHeight()
                        .padding(end = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = poke.offArtwork,
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp)
                            .graphicsLayer {
                                scaleX = 1.03f
                                scaleY = 1.03f
                                alpha = 0.25f
                            }
                            .blur(4.dp),
                        colorFilter = ColorFilter.tint(Color.Black)
                    )
                    AsyncImage(
                        model = poke.offArtwork,
                        contentDescription = poke.name,
                        modifier = Modifier.size(80.dp)
                    )
                }
            }
        }
    }
}