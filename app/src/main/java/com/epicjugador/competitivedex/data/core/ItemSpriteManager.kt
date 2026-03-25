package com.epicjugador.competitivedex.data.core

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

object ItemSpriteManager {

    private const val SPRITE_URL =
        "https://play.pokemonshowdown.com/sprites/itemicons-sheet.png"

    private var spriteSheet: Bitmap? = null

    // ⚠️ referencia fuerte para evitar GC
    private var picassoTarget: Target? = null

    fun loadSprites(onLoaded: () -> Unit) {

        if (spriteSheet != null) {
            onLoaded()
            return
        }

        picassoTarget = object : Target {

            override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
                spriteSheet = bitmap
                onLoaded()
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {}

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
        }

        Picasso.get()
            .load(SPRITE_URL)
            .into(picassoTarget!!)
    }

    fun getSprite(spriteNum: Int, size: Int, columns: Int): Bitmap? {

        val sheet = spriteSheet ?: return null

        val x = (spriteNum % columns) * size
        val y = (spriteNum / columns) * size

        return Bitmap.createBitmap(sheet, x, y, size, size)
    }
}