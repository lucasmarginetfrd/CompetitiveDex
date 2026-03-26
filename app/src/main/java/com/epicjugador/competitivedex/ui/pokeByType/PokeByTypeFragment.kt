package com.epicjugador.competitivedex.ui.pokeByType

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.epicjugador.competitivedex.domain.model.Poke
import com.epicjugador.competitivedex.domain.model.PokeData
import com.epicjugador.competitivedex.domain.model.PokeTypeInfo
import com.epicjugador.competitivedex.domain.model.PokeTypeModel
import com.epicjugador.competitivedex.ui.pokeByType.Compose.PokeByTypeScreen
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokeByTypeFragment : Fragment() {

    private val viewModel by viewModels<PokeTypeViewModel>()

    private var pokeList = listOf<PokeData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getPoke()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // 🔄 Reemplaza XML completamente
        return ComposeView(requireContext()).apply {

            setContent {

                PokeByTypeScreen(
                    viewModel = viewModel,

                    // 🔄 Reemplaza onClick del adapter
                    onTypeSelected = { typeInfo ->

                        val type = when (typeInfo) {
                            PokeTypeInfo.Grass -> PokeTypeModel.Grass
                            PokeTypeInfo.Fire -> PokeTypeModel.Fire
                            PokeTypeInfo.Water -> PokeTypeModel.Water
                            PokeTypeInfo.Bug -> PokeTypeModel.Bug
                            PokeTypeInfo.Normal -> PokeTypeModel.Normal
                            PokeTypeInfo.Poison -> PokeTypeModel.Poison
                            PokeTypeInfo.Electric -> PokeTypeModel.Electric
                            PokeTypeInfo.Ground -> PokeTypeModel.Ground
                            PokeTypeInfo.Fairy -> PokeTypeModel.Fairy
                            PokeTypeInfo.Fighting -> PokeTypeModel.Fighting
                            PokeTypeInfo.Psychic -> PokeTypeModel.Psychic
                            PokeTypeInfo.Rock -> PokeTypeModel.Rock
                            PokeTypeInfo.Ghost -> PokeTypeModel.Ghost
                            PokeTypeInfo.Ice -> PokeTypeModel.Ice
                            PokeTypeInfo.Dragon -> PokeTypeModel.Dragon
                            PokeTypeInfo.Dark -> PokeTypeModel.Dark
                            PokeTypeInfo.Steel -> PokeTypeModel.Steel
                            PokeTypeInfo.Flying -> PokeTypeModel.Flying
                        }

                        val pokeTyping = type.name.lowercase()

                        val pokeListByType = pokeList.filter { poke ->
                            poke.types.any {
                                it.type.name.contains(pokeTyping, true)
                            }
                        }

                        val action = PokeByTypeFragmentDirections
                            .actionPokeByTypeFragmentToPokeListFragment(
                                pokeListByType.toTypedArray()
                            )

                        findNavController().navigate(action)
                    }
                )
            }
        }
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
}