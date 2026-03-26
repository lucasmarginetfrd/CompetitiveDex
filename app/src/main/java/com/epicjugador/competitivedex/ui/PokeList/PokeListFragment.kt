package com.epicjugador.competitivedex.ui.PokeList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.epicjugador.competitivedex.ui.PokeList.Compose.PokeListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokeListFragment : Fragment() {

    private val args: PokeListFragmentArgs by navArgs()
    private val viewModel: PokeListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                PokeListScreen(
                    viewModel = viewModel,
                    onPokemonClick = { id ->
                        findNavController().navigate(
                            PokeListFragmentDirections
                                .actionPokeListFragmentToPokeDetailActivity(id)
                        )
                    }
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.loadPokemon(args.pokeListByType.toList())
    }
}