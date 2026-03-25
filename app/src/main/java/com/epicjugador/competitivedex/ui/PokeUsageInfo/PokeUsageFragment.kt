package com.epicjugador.competitivedex.ui.PokeUsageInfo

import android.R.attr.text
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.epicjugador.competitivedex.databinding.FragmentPokeUsageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokeUsageFragment : Fragment() {

    private var _binding: FragmentPokeUsageBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PokeUsageStatsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokeUsageBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadStats()
        viewModel.pokemonStats.observe(viewLifecycleOwner) { stats ->
            val pokemon = stats["Flutter Mane"]
            val usage = pokemon?.usage

            binding.tvPokeUsage.text = "Flutter Mane = $usage%"

            val urshifu = stats["Urshifu-Rapid-Strike"]
            val items = urshifu?.items
            val top10items = items
                ?.toList()
                ?.sortedByDescending { it.second }
                ?.take(10)
            val textItems = StringBuilder()

            top10items?.forEach { (items, usage) ->
                textItems.append("$items: $usage%\n")
            }

            binding.tvPokeItems.text = textItems.toString()
        }
    }
}
