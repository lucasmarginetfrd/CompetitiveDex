package com.epicjugador.competitivedex.ui.detail

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.epicjugador.competitivedex.R
import com.epicjugador.competitivedex.data.core.ItemSpriteManager
import com.epicjugador.competitivedex.databinding.ActivityPokeDetailBinding
import com.epicjugador.competitivedex.domain.model.UsageListItem
import com.epicjugador.competitivedex.ui.detail.adapterDetail.UsageAdapter
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.round

@AndroidEntryPoint
class PokeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokeDetailBinding
    private val pokeDetailViewModel: PokeDetailViewModel by viewModels()

    private val flippedStates = mutableMapOf<Int, Boolean>()

    private val args: PokeDetailActivityArgs by navArgs()

    private val itemsAdapter = UsageAdapter()
    private val abilitiesAdapter = UsageAdapter()
    private val movesAdapter = UsageAdapter()
    private var currentSmogonName: String? = null

    private lateinit var abilityNames: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokeDetailBinding.inflate(layoutInflater)
        binding.recyclerItems.adapter = itemsAdapter
        binding.recyclerItems.layoutManager = LinearLayoutManager(this)
        binding.recyclerAbilities.adapter = abilitiesAdapter
        binding.recyclerAbilities.layoutManager = LinearLayoutManager(this)
        binding.recyclerMoves.adapter = movesAdapter
        binding.recyclerMoves.layoutManager = LinearLayoutManager(this)
        setContentView(binding.root)
        ItemSpriteManager.loadSprites {
            itemsAdapter.notifyDataSetChanged()
        }
        initUI()
        pokeDetailViewModel.getPokeDetails(args.pokeId)
        pokeDetailViewModel.loadStats()
        pokeDetailViewModel.pokemonStats.observe(this) { stats ->
            val smogonName = currentSmogonName ?: return@observe
            val pokemon = stats[smogonName]

            val usage = roundDecimals((pokemon?.usage?.times(100)))
            binding.tvPokeUsage.text = "Usage = $usage%"

            val itemsList = mutableListOf<UsageListItem>()
            val abilitiesList = mutableListOf<UsageListItem>()
            val movesList = mutableListOf<UsageListItem>()

            val totalUsage = pokemon?.abilities?.values?.sum() ?: 0.0

            pokemon?.items?.let {
                itemsList.add(UsageListItem.Header("Items"))

                it.toList()
                    .sortedByDescending { pair -> pair.second }
                    .take(6)
                    .forEach { (item, usage) ->

                        val percent = (usage / totalUsage) * 100

                        val spriteNum = pokeDetailViewModel.getItemSpriteNum(item)

                        itemsList.add(
                            UsageListItem.Entry(
                                name = pokeDetailViewModel.getItemDisplayName(item),
                                usage = percent,
                                spriteNum = spriteNum
                            )
                        )
                    }
            }

            pokemon?.abilities?.let {
                abilitiesList.add(UsageListItem.Header("Abilities"))

                it.toList()
                    .sortedByDescending { pair -> pair.second }
                    .take(3)
                    .forEach { (ability, usage) ->

                        val percent = (usage / totalUsage) * 100

                        abilitiesList.add(
                            UsageListItem.Entry(
                                name = pokeDetailViewModel.getAbilityName(ability),
                                usage = percent
                            )
                        )
                    }
            }

            pokemon?.moves?.let {
                movesList.add(UsageListItem.Header("Moves"))

                it.toList()
                    .sortedByDescending { pair -> pair.second }
                    .take(6)
                    .forEach { (move, usage) ->

                        val percent = (usage / totalUsage) * 100

                        movesList.add(
                            UsageListItem.Entry(
                                name = pokeDetailViewModel.getMoveName(move),
                                usage = percent
                            )
                        )
                    }
            }

            itemsAdapter.submitList(itemsList)
            abilitiesAdapter.submitList(abilitiesList)
            movesAdapter.submitList(movesList)
        }
    }

    private fun initUI() {
        //initCards()
        initUIState()
    }

    /*private fun initCards() {
        setupCardFlip(
            abilityContainer = binding.card1,
            abilityFront = binding.cardFront1,
            //abilityBack = binding.cardBack1
        )

        setupCardFlip(
            abilityContainer = binding.card2,
            abilityFront = binding.cardFront2,
            //abilityBack = binding.cardBack2
        )

        setupCardFlip(
            abilityContainer = binding.card3,
            abilityFront = binding.cardFrontHidden,
            abilityBack = binding.cardBackHidden
        )
    }*/

    /*private fun setupCardFlip(
        abilityContainer: FrameLayout,
        abilityFront: CardView,
        abilityBack: CardView
    ) {
        val scale = applicationContext.resources.displayMetrics.density
        abilityFront.cameraDistance = 8000 * scale
        abilityBack.cameraDistance = 8000 * scale

        flippedStates[abilityContainer.id]

        abilityContainer.setOnClickListener {

            abilityContainer.isClickable = false

            val isBackVisible = flippedStates[abilityContainer.id] ?: false

            val flipOut = AnimatorInflater.loadAnimator(this, R.animator.flip_out)
            val flipIn = AnimatorInflater.loadAnimator(this, R.animator.flip_in)

            if (!isBackVisible) {
                abilityBack.visibility = View.VISIBLE
                flipOut.setTarget(abilityFront)
                flipIn.setTarget(abilityBack)
                flipOut.start()
                flipIn.start()

                abilityFront.postDelayed({
                    abilityFront.visibility = View.GONE
                    flippedStates[abilityContainer.id] = true
                    abilityContainer.isClickable = true
                }, 300)
            } else {
                abilityFront.visibility = View.VISIBLE
                flipOut.setTarget(abilityBack)
                flipIn.setTarget(abilityFront)
                flipOut.start()
                flipIn.start()

                abilityBack.postDelayed({
                    abilityBack.visibility = View.GONE
                    flippedStates[abilityContainer.id] = false
                    abilityContainer.isClickable = true
                }, 300)
            }

            flippedStates[abilityContainer.id] = !isBackVisible
        }
    }*/

    private fun initUIState() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                pokeDetailViewModel.state.collect {
                    when (it) {
                        is PokeDetailState.Error -> errorState()
                        PokeDetailState.Loading -> loadingState()
                        is PokeDetailState.Success -> successState(it)
                        is PokeDetailState.SuccessAbilities -> successStateAbilities(it)
                    }
                }
            }
        }
    }

    fun formatName(name: String): String {
        val parts = name.lowercase().split("-").toMutableList()

        val hasMega = parts.remove("mega")

        val formattedParts = parts.map { part ->
            part.replaceFirstChar { it.uppercase() }
        }.toMutableList()

        if (hasMega) {
            formattedParts.add(0, "Mega")
        }

        return formattedParts.joinToString(" ")
    }
    fun formatNameSmogon(name: String): String {
        val parts = name.lowercase().split("-").toMutableList()
        val hasMega = parts.remove("mega")
        parts.remove("breed")
        parts.remove("mask")
        parts.remove("incarnate")

        val prefixParadox = setOf(
            "flutter",
            "great",
            "roaring",
            "scream",
            "brute",
            "sandy",
            "slither",
            "walking",
            "raging",
            "gouging"
        )
        val pastParadox = parts.firstOrNull() in prefixParadox
        val futureParadox = parts.firstOrNull() == "iron"

        val formattedParts = parts.map { part ->
            part.replaceFirstChar { it.uppercase() }
        }.toMutableList()
        if (hasMega) {
            formattedParts.add(0, "Mega")
        }
        if (pastParadox || futureParadox) {
            return formattedParts.joinToString(" ")
        }

        return formattedParts.joinToString("-")

    }

    fun roundDecimals(value: Double?): Double {
        return round(value?.times(100) ?: 0.0) / 100
    }

    private fun successState(state: PokeDetailState.Success) {
        val pokemonFormattedName = formatName(state.name)
        currentSmogonName = formatNameSmogon(state.name)

        binding.pokeDetailName.text = pokemonFormattedName

        binding.pokeDetailType1.text = state.types[0].replaceFirstChar { it.uppercase() }
        if (state.types.size == 2) {
            binding.pokeDetailType1.visibility = View.VISIBLE
            binding.pokeDetailType2.text = state.types[1].replaceFirstChar { it.uppercase() }
        } else {
            binding.pokeDetailType2.text = ""
            binding.pokeDetailType2.visibility = View.GONE
        }

        when (state.types[0]) {
            "grass" -> binding.ivPokeType1.setImageResource(R.drawable.ic_grass)
            "fire" -> binding.ivPokeType1.setImageResource(R.drawable.ic_fire)
            "water" -> binding.ivPokeType1.setImageResource(R.drawable.ic_water)
            "bug" -> binding.ivPokeType1.setImageResource(R.drawable.ic_bug)
            "normal" -> binding.ivPokeType1.setImageResource(R.drawable.ic_normal)
            "poison" -> binding.ivPokeType1.setImageResource(R.drawable.ic_poison)
            "electric" -> binding.ivPokeType1.setImageResource(R.drawable.ic_electric)
            "ground" -> binding.ivPokeType1.setImageResource(R.drawable.ic_ground)
            "fairy" -> binding.ivPokeType1.setImageResource(R.drawable.ic_fairy)
            "fighting" -> binding.ivPokeType1.setImageResource(R.drawable.ic_fighting)
            "psychic" -> binding.ivPokeType1.setImageResource(R.drawable.ic_psychic)
            "rock" -> binding.ivPokeType1.setImageResource(R.drawable.ic_rock)
            "ghost" -> binding.ivPokeType1.setImageResource(R.drawable.ic_ghost)
            "ice" -> binding.ivPokeType1.setImageResource(R.drawable.ic_ice)
            "dragon" -> binding.ivPokeType1.setImageResource(R.drawable.ic_dragon)
            "dark" -> binding.ivPokeType1.setImageResource(R.drawable.ic_dark)
            "steel" -> binding.ivPokeType1.setImageResource(R.drawable.ic_steel)
            "flying" -> binding.ivPokeType1.setImageResource(R.drawable.ic_flying)
        }

        if (state.types.size == 2) {
            when (state.types[1]) {
                "grass" -> binding.ivPokeType2.setImageResource(R.drawable.ic_grass)
                "fire" -> binding.ivPokeType2.setImageResource(R.drawable.ic_fire)
                "water" -> binding.ivPokeType2.setImageResource(R.drawable.ic_water)
                "bug" -> binding.ivPokeType2.setImageResource(R.drawable.ic_bug)
                "normal" -> binding.ivPokeType2.setImageResource(R.drawable.ic_normal)
                "poison" -> binding.ivPokeType2.setImageResource(R.drawable.ic_poison)
                "electric" -> binding.ivPokeType2.setImageResource(R.drawable.ic_electric)
                "ground" -> binding.ivPokeType2.setImageResource(R.drawable.ic_ground)
                "fairy" -> binding.ivPokeType2.setImageResource(R.drawable.ic_fairy)
                "fighting" -> binding.ivPokeType2.setImageResource(R.drawable.ic_fighting)
                "psychic" -> binding.ivPokeType2.setImageResource(R.drawable.ic_psychic)
                "rock" -> binding.ivPokeType2.setImageResource(R.drawable.ic_rock)
                "ghost" -> binding.ivPokeType2.setImageResource(R.drawable.ic_ghost)
                "ice" -> binding.ivPokeType2.setImageResource(R.drawable.ic_ice)
                "dragon" -> binding.ivPokeType2.setImageResource(R.drawable.ic_dragon)
                "dark" -> binding.ivPokeType2.setImageResource(R.drawable.ic_dark)
                "steel" -> binding.ivPokeType2.setImageResource(R.drawable.ic_steel)
                "flying" -> binding.ivPokeType2.setImageResource(R.drawable.ic_flying)
            }
        } else {
            binding.ivPokeType2.visibility = View.GONE
            binding.divider.visibility = View.GONE
        }

        binding.card1.visibility = View.GONE
        binding.card2.visibility = View.GONE
        binding.card3.visibility = View.GONE

        when (state.abilities.size) {
            1 -> {
                binding.card1.visibility = View.VISIBLE
                binding.pokeDetailAbility1.text =
                    state.abilities[0].replaceFirstChar { it.uppercase() }
                abilityNames = listOf(state.abilities[0])
                pokeDetailViewModel.loadAbilities(abilityNames)
            }

            2 -> {
                binding.card1.visibility = View.VISIBLE
                binding.pokeDetailAbility1.text =
                    state.abilities[0].replaceFirstChar { it.uppercase() }
                abilityNames = (listOf(state.abilities[0]) + state.abilities[1])
                pokeDetailViewModel.loadAbilities(abilityNames)

                if (state.isHidden) {
                    binding.card3.visibility = View.VISIBLE
                    val abilityName = state.abilities[1].replaceFirstChar { it.uppercase() }
                    val spannableString = SpannableString(abilityName)
                    spannableString.setSpan(
                        StyleSpan(Typeface.ITALIC),
                        0,
                        abilityName.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    binding.pokeDetailHiddenAbility.text = spannableString
                } else {
                    binding.card2.visibility = View.VISIBLE
                    binding.pokeDetailAbility2.text =
                        state.abilities[1].replaceFirstChar { it.uppercase() }
                }
            }

            3 -> {
                binding.card1.visibility = View.VISIBLE
                binding.pokeDetailAbility1.text =
                    state.abilities[0].replaceFirstChar { it.uppercase() }
                binding.card2.visibility = View.VISIBLE
                binding.card3.visibility = View.VISIBLE

                abilityNames = state.abilities
                pokeDetailViewModel.loadAbilities(abilityNames)

                binding.pokeDetailAbility2.text =
                    state.abilities[1].replaceFirstChar { it.uppercase() }

                val abilityName = state.abilities[2].replaceFirstChar { it.uppercase() }
                val spannableString = SpannableString(abilityName)
                spannableString.setSpan(
                    StyleSpan(Typeface.ITALIC),
                    0,
                    abilityName.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                binding.pokeDetailHiddenAbility.text = spannableString
            }
        }

        Picasso
            .get()
            .load(state.offArtwork)
            .into(binding.ivPokeDetail)

        binding.composeView.setContent {
            StatsChart(
                data = mapOf(
                    state.statName[0].uppercase() to state.baseStats[0],
                    state.statName[1].replaceFirstChar { it.uppercase() } to state.baseStats[1],
                    state.statName[2].replaceFirstChar { it.uppercase() } to state.baseStats[2],
                    "Sp.Atk." to state.baseStats[3],
                    "Sp.Def" to state.baseStats[4],
                    state.statName[5].replaceFirstChar { it.uppercase() } to state.baseStats[5],
                ), maxValue = 255
            )
        }

    }

    private fun successStateAbilities(state: PokeDetailState.SuccessAbilities) {
        //binding.pokeDetailAbilityEffect1.text = state.effects[0]
        /*state.effects[abilityNames[0]]?.let {
            binding.pokeDetailAbilityEffect1.text = it
        }*/

        if (binding.card2.isVisible && binding.card3.isVisible && state.effects.size > 2) {
            //binding.pokeDetailAbilityEffect2.text = state.effects[1]

            /*state.effects[abilityNames[1]]?.let {
                binding.pokeDetailAbilityEffect2.text = it
            }*/

            //val abilityEffect = state.effects[2]
            state.effects[abilityNames[2]]?.let {
                val spannableString = SpannableString(it)
                spannableString.setSpan(
                    StyleSpan(Typeface.ITALIC),
                    0,
                    it.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                //binding.pokeDetailAbilityEffectHidden.text = spannableString
            }

        } else if (binding.card2.isVisible && binding.card3.isGone && state.effects.size > 1) {
            //binding.pokeDetailAbilityEffect2.text = state.effects[1]
            /*state.effects[abilityNames[1]]?.let {
                binding.pokeDetailAbilityEffect2.text = it
            }*/
        } else if (binding.card3.isVisible && binding.card2.isGone && state.effects.size > 1) {
            //val abilityEffect = state.effects[1]
            state.effects[abilityNames[1]]?.let {
                val spannableString = SpannableString(it)
                spannableString.setSpan(
                    StyleSpan(Typeface.ITALIC),
                    0,
                    it.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                //binding.pokeDetailAbilityEffectHidden.text = spannableString
            }
        }

        Log.i("effect", state.effects.toString())
    }

    private fun loadingState() {

    }

    private fun errorState() {

    }
}