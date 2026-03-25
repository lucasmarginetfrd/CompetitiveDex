package com.epicjugador.competitivedex.ui.pokeByType

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.epicjugador.competitivedex.databinding.FragmentPokeByTypeBinding
import com.epicjugador.competitivedex.domain.model.Poke
import com.epicjugador.competitivedex.domain.model.PokeData
import com.epicjugador.competitivedex.domain.model.PokeTypeInfo.Bug
import com.epicjugador.competitivedex.domain.model.PokeTypeInfo.Dark
import com.epicjugador.competitivedex.domain.model.PokeTypeInfo.Dragon
import com.epicjugador.competitivedex.domain.model.PokeTypeInfo.Electric
import com.epicjugador.competitivedex.domain.model.PokeTypeInfo.Fairy
import com.epicjugador.competitivedex.domain.model.PokeTypeInfo.Fighting
import com.epicjugador.competitivedex.domain.model.PokeTypeInfo.Fire
import com.epicjugador.competitivedex.domain.model.PokeTypeInfo.Flying
import com.epicjugador.competitivedex.domain.model.PokeTypeInfo.Ghost
import com.epicjugador.competitivedex.domain.model.PokeTypeInfo.Grass
import com.epicjugador.competitivedex.domain.model.PokeTypeInfo.Ground
import com.epicjugador.competitivedex.domain.model.PokeTypeInfo.Ice
import com.epicjugador.competitivedex.domain.model.PokeTypeInfo.Normal
import com.epicjugador.competitivedex.domain.model.PokeTypeInfo.Poison
import com.epicjugador.competitivedex.domain.model.PokeTypeInfo.Psychic
import com.epicjugador.competitivedex.domain.model.PokeTypeInfo.Rock
import com.epicjugador.competitivedex.domain.model.PokeTypeInfo.Steel
import com.epicjugador.competitivedex.domain.model.PokeTypeInfo.Water
import com.epicjugador.competitivedex.domain.model.PokeTypeModel
import com.epicjugador.competitivedex.ui.pokeByType.adapterType.PokeTypeAdapter
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokeByTypeFragment : Fragment() {

    private var _binding: FragmentPokeByTypeBinding? = null
    private val binding get() = _binding!!

    private val pokeTypeViewModel by viewModels<PokeTypeViewModel>()

    private lateinit var adapterTypes: PokeTypeAdapter

    private var pokeList = listOf<PokeData>()
    private var pokeListByType = listOf<PokeData>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        getPoke()
        initList()
        initUIState()
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                pokeTypeViewModel.types.collect {
                    adapterTypes.updateList(it)
                }
            }
        }
    }

    private fun initList() {
        adapterTypes = PokeTypeAdapter(onTypeSelected = {
            val type = when (it) {
                Grass -> PokeTypeModel.Grass
                Fire -> PokeTypeModel.Fire
                Water -> PokeTypeModel.Water
                Bug -> PokeTypeModel.Bug
                Normal -> PokeTypeModel.Normal
                Poison -> PokeTypeModel.Poison
                Electric -> PokeTypeModel.Electric
                Ground -> PokeTypeModel.Ground
                Fairy -> PokeTypeModel.Fairy
                Fighting -> PokeTypeModel.Fighting
                Psychic -> PokeTypeModel.Psychic
                Rock -> PokeTypeModel.Rock
                Ghost -> PokeTypeModel.Ghost
                Ice -> PokeTypeModel.Ice
                Dragon -> PokeTypeModel.Dragon
                Dark -> PokeTypeModel.Dark
                Steel -> PokeTypeModel.Steel
                Flying -> PokeTypeModel.Flying
            }
            val pokeTyping = type.name.lowercase()
            filterPoke(pokeTyping)
        })

        binding.rvTyping.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapterTypes
        }
    }

    private fun filterPoke(typing: String) {
        pokeListByType = pokeList.filter { poke ->
            poke.types.any { it.type.name.contains(typing, true) }
        }
        Log.i("pokeList", pokeListByType.toString())
        val action = PokeByTypeFragmentDirections
            .actionPokeByTypeFragmentToPokeListFragment(pokeListByType.toTypedArray())

        findNavController().navigate(action)
    }

    private fun loadData(inFile: String): String {
        var tContents = ""
        try {
            val stream = requireContext().assets.open(inFile)
            val size = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            tContents = String(buffer)
        } catch (_: Exception) {}
        return tContents
    }

    private fun getPoke() {
        CoroutineScope(Dispatchers.IO).launch {
            val json = loadData("pokemon_list.json")
            val gson = Gson()
            val poke = gson.fromJson(json, Poke::class.java)
            requireActivity().runOnUiThread {
                pokeList = poke.pokemon.map { it }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokeByTypeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}