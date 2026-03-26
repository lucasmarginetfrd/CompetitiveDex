package com.epicjugador.competitivedex.ui.detail.Compose

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.epicjugador.competitivedex.R

@Composable
fun StatsChart(
    data: Map<String, Int>,
    maxValue: Int
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        data.forEach { (statName, value) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.width(60.dp),
                    text = statName,
                    textAlign = TextAlign.Start
                )

                Text(
                    modifier = Modifier.width(40.dp),
                    text = value.toString(),
                    textAlign = TextAlign.Center
                )

                val targetProgress = value.toFloat() / maxValue.toFloat()
                var startAnimation by remember { mutableStateOf(false) }

                LaunchedEffect(Unit) {
                    startAnimation = true
                }

                val animatedProgress by animateFloatAsState(
                    targetValue = if (startAnimation) targetProgress else 0f,
                    animationSpec = tween(
                        durationMillis = 1000,
                        delayMillis = 50,
                        easing = FastOutSlowInEasing
                    ), label = ""
                )


                val barColor = lerp(
                    colorResource(id = R.color.lowerStat),
                    colorResource(id = R.color.higherStat),
                    value.toFloat() / maxValue.toFloat()
                )

                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .height(20.dp)
                        .fillMaxWidth(animatedProgress)
                        .clip(RoundedCornerShape(10.dp))
                        .background(barColor)
                )

            }
        }
    }
}