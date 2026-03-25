package com.epicjugador.competitivedex.data.local

import com.epicjugador.competitivedex.data.ItemData

object ShowdownCache {
    val items = HashMap<String, ItemData>(700)
    val moves = HashMap<String, ItemData>(1000)
    val abilities = HashMap<String, ItemData>(300)

    var loaded = false
}