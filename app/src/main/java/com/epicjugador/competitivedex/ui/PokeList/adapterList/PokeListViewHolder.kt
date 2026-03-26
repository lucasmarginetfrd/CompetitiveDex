package com.epicjugador.competitivedex.ui.PokeList.adapterList
/*
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.epicjugador.competitivedex.R
import com.epicjugador.competitivedex.databinding.ItemPokeListBinding
import com.epicjugador.competitivedex.domain.model.PokeData
import com.squareup.picasso.Picasso

class PokeListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemPokeListBinding.bind(view)

    fun formatName(name: String): String {
        return name
            .lowercase()
            .split("-")
            .filter { it != "mega" }
            .map { it.replaceFirstChar(Char::uppercase) }
            .let {
                if (name.contains("-mega")) listOf("Mega") + it else it
            }
            .joinToString(" ")
    }

    fun bind(poke: PokeData, onPokeSelected: (Int) -> Unit) {
        Picasso.get()
            .load(poke.offArtwork)
            .into(binding.ivPokeInList)
        binding.tvPokeInListName.text = formatName(poke.name)
        binding.tvPokeTypeInList.text = poke.types[0].type.name.replaceFirstChar { it.uppercase() }

        /*when (poke.types[0].type.name) {
            "grass" -> binding.cvType1.setCardBackgroundColor(ContextCompat.getColor(binding.cvType1.context, R.color.grass))
            "fire" -> binding.cvType1.setCardBackgroundColor(ContextCompat.getColor(binding.cvType1.context, R.color.fire))
            "water" -> binding.cvType1.setCardBackgroundColor(ContextCompat.getColor(binding.cvType1.context, R.color.water))
            "bug" -> binding.cvType1.setCardBackgroundColor(ContextCompat.getColor(binding.cvType1.context, R.color.bug))
            "normal" -> binding.cvType1.setCardBackgroundColor(ContextCompat.getColor(binding.cvType1.context, R.color.normal))
            "poison" -> binding.cvType1.setCardBackgroundColor(ContextCompat.getColor(binding.cvType1.context, R.color.poison))
            "electric" -> binding.cvType1.setCardBackgroundColor(ContextCompat.getColor(binding.cvType1.context, R.color.electric))
            "ground" -> binding.cvType1.setCardBackgroundColor(ContextCompat.getColor(binding.cvType1.context, R.color.ground))
            "fairy" -> binding.cvType1.setCardBackgroundColor(ContextCompat.getColor(binding.cvType1.context, R.color.fairy))
            "fighting" -> binding.cvType1.setCardBackgroundColor(ContextCompat.getColor(binding.cvType1.context, R.color.fighting))
            "psychic" -> binding.cvType1.setCardBackgroundColor(ContextCompat.getColor(binding.cvType1.context, R.color.psychic))
            "rock" -> binding.cvType1.setCardBackgroundColor(ContextCompat.getColor(binding.cvType1.context, R.color.rock))
            "ghost" -> binding.cvType1.setCardBackgroundColor(ContextCompat.getColor(binding.cvType1.context, R.color.ghost))
            "ice" -> binding.cvType1.setCardBackgroundColor(ContextCompat.getColor(binding.cvType1.context, R.color.ice))
            "dragon" -> binding.cvType1.setCardBackgroundColor(ContextCompat.getColor(binding.cvType1.context, R.color.dragon))
            "dark" -> binding.cvType1.setCardBackgroundColor(ContextCompat.getColor(binding.cvType1.context, R.color.dark))
            "steel" -> binding.cvType1.setCardBackgroundColor(ContextCompat.getColor(binding.cvType1.context, R.color.steel))
            "flying" -> binding.cvType1.setCardBackgroundColor(ContextCompat.getColor(binding.cvType1.context, R.color.flying))
        }*/
        binding.cvType1.setCardBackgroundColor(
            getTypeColor(binding.cvType1.context, poke.types[0].type.name)
        )

        if (poke.types.size == 2) {
            binding.tvPokeType2InList.visibility = View.VISIBLE
            binding.tvPokeType2InList.text = poke.types[1].type.name.replaceFirstChar { it.uppercase() }
            binding.cvType2.setCardBackgroundColor(
                getTypeColor(binding.cvType2.context, poke.types[1].type.name)
            )
            /*when (poke.types[1].type.name) {
                "grass" -> binding.cvType2.setCardBackgroundColor(ContextCompat.getColor(binding.cvType2.context, R.color.grass))
                "fire" -> binding.cvType2.setCardBackgroundColor(ContextCompat.getColor(binding.cvType2.context, R.color.fire))
                "water" -> binding.cvType2.setCardBackgroundColor(ContextCompat.getColor(binding.cvType2.context, R.color.water))
                "bug" -> binding.cvType2.setCardBackgroundColor(ContextCompat.getColor(binding.cvType2.context, R.color.bug))
                "normal" -> binding.cvType2.setCardBackgroundColor(ContextCompat.getColor(binding.cvType2.context, R.color.normal))
                "poison" -> binding.cvType2.setCardBackgroundColor(ContextCompat.getColor(binding.cvType2.context, R.color.poison))
                "electric" -> binding.cvType2.setCardBackgroundColor(ContextCompat.getColor(binding.cvType2.context, R.color.electric))
                "ground" -> binding.cvType2.setCardBackgroundColor(ContextCompat.getColor(binding.cvType2.context, R.color.ground))
                "fairy" -> binding.cvType2.setCardBackgroundColor(ContextCompat.getColor(binding.cvType2.context, R.color.fairy))
                "fighting" -> binding.cvType2.setCardBackgroundColor(ContextCompat.getColor(binding.cvType2.context, R.color.fighting))
                "psychic" -> binding.cvType2.setCardBackgroundColor(ContextCompat.getColor(binding.cvType2.context, R.color.psychic))
                "rock" -> binding.cvType2.setCardBackgroundColor(ContextCompat.getColor(binding.cvType2.context, R.color.rock))
                "ghost" -> binding.cvType2.setCardBackgroundColor(ContextCompat.getColor(binding.cvType2.context, R.color.ghost))
                "ice" -> binding.cvType2.setCardBackgroundColor(ContextCompat.getColor(binding.cvType2.context, R.color.ice))
                "dragon" -> binding.cvType2.setCardBackgroundColor(ContextCompat.getColor(binding.cvType2.context, R.color.dragon))
                "dark" -> binding.cvType2.setCardBackgroundColor(ContextCompat.getColor(binding.cvType2.context, R.color.dark))
                "steel" -> binding.cvType2.setCardBackgroundColor(ContextCompat.getColor(binding.cvType2.context, R.color.steel))
                "flying" -> binding.cvType2.setCardBackgroundColor(ContextCompat.getColor(binding.cvType2.context, R.color.flying))
            }*/
        } else {
            binding.tvPokeType2InList.text = ""
            binding.tvPokeType2InList.visibility = View.GONE
        }

        applyTypeGradientBackground(
            binding.pokeListCardView,
            poke.types[0].type.name,
            poke.types.getOrNull(1)?.type?.name
        )

        binding.root.setOnClickListener {
            onPokeSelected(poke.id)
        }

    }

    private fun getTypeColor(context: Context, type: String): Int {
        return when (type.lowercase()) {
            "grass" -> ContextCompat.getColor(context, R.color.grass)
            "fire" -> ContextCompat.getColor(context, R.color.fire)
            "water" -> ContextCompat.getColor(context, R.color.water)
            "bug" -> ContextCompat.getColor(context, R.color.bug)
            "normal" -> ContextCompat.getColor(context, R.color.normal)
            "poison" -> ContextCompat.getColor(context, R.color.poison)
            "electric" -> ContextCompat.getColor(context, R.color.electric)
            "ground" -> ContextCompat.getColor(context, R.color.ground)
            "fairy" -> ContextCompat.getColor(context, R.color.fairy)
            "fighting" -> ContextCompat.getColor(context, R.color.fighting)
            "psychic" -> ContextCompat.getColor(context, R.color.psychic)
            "rock" -> ContextCompat.getColor(context, R.color.rock)
            "ghost" -> ContextCompat.getColor(context, R.color.ghost)
            "ice" -> ContextCompat.getColor(context, R.color.ice)
            "dragon" -> ContextCompat.getColor(context, R.color.dragon)
            "dark" -> ContextCompat.getColor(context, R.color.dark)
            "steel" -> ContextCompat.getColor(context, R.color.steel)
            "flying" -> ContextCompat.getColor(context, R.color.flying)
            else -> {ContextCompat.getColor(context, R.color.white)}
        }
    }

    private fun applyTypeGradientBackground(pokeCard: ConstraintLayout, type1: String, type2: String? = null) {
        val context = pokeCard.context

        val type1Color = getTypeColor(context, type1)
        val type2Color = type2?.let { getTypeColor(context, it) } ?: lightenColor(type1Color, 0.7f)

        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.LEFT_RIGHT,
            intArrayOf(type1Color, type2Color)
        )

        pokeCard.background = gradientDrawable
    }

    private fun lightenColor(color: Int, factor: Float): Int {
        val r = ((color shr 16 and 0xFF) + ((255 - (color shr 16 and 0xFF)) * factor)).toInt()
        val g = ((color shr 8 and 0xFF) + ((255 - (color shr 8 and 0xFF)) * factor)).toInt()
        val b = ((color and 0xFF) + ((255 - (color and 0xFF)) * factor)).toInt()
        return (0xFF shl 24) or (r shl 16) or (g shl 8) or b
    }

}*/