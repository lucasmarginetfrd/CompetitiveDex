package com.epicjugador.competitivedex.ui.PokeList

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.epicjugador.competitivedex.R
import com.epicjugador.competitivedex.databinding.FragmentPokeListBinding
import com.epicjugador.competitivedex.domain.model.PokeData
import com.epicjugador.competitivedex.ui.PokeList.adapterList.PokeListAdapter
import com.epicjugador.competitivedex.ui.PokeList.adapterList.PokeListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokeListFragment : Fragment() {

    private var _binding: FragmentPokeListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapterPokeList: PokeListAdapter

    private val args: PokeListFragmentArgs by navArgs()
    private lateinit var pokeList: List<PokeData>

    private val viewModel: PokeListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokeListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initAdapter()
        viewModel.filteredPokemonList.observe(viewLifecycleOwner) { list ->
            adapterPokeList.submitList(list)
        }
        binding.searchPokemon.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchPokemon(query ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchPokemon(newText ?: "")
                return true
            }
        })
    }


    private fun initAdapter() {
        adapterPokeList = PokeListAdapter { id -> findNavController().navigate(
            PokeListFragmentDirections.actionPokeListFragmentToPokeDetailActivity(id)
        )}


        binding.rvPokeList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapterPokeList
            setHasFixedSize(true)
            itemAnimator = null
        }
    }

    private fun initUI() {
        pokeList = args.pokeListByType.toList()
        viewModel.loadPokemon(pokeList)
    }

}