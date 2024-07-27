package com.example.presentation.ui.home

import android.app.Application
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import app.futured.donut.DonutSection
import com.example.presentation.R
import com.example.presentation.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!
    private val homeViewModel:HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        homeViewModel.updateNumbers()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                homeViewModel.numberListFlow.filter { it.isNotEmpty() }.collect{
                    val shorts = it[0]
                    val longs = it[1]
                    val wins = it[2]
                    val defeats = it[3]
                    donut1(shorts,longs)
                    donut2(wins,defeats)
                }
            }
        }
        binding.dateCard.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_navigation_home_to_dateActivity)
        }
        binding.materialsCard.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_navigation_home_to_materialsActivity)
        }
        binding.indicatorCard.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_navigation_home_to_instrumentActivity)
        }
        binding.strategyCard.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_navigation_home_to_strategyActivity)
        }
        binding.analysisCard.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_navigation_home_to_analysisActivity)
        }
        binding.resultsCard.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_navigation_home_to_resultsActivity)
        }
        binding.noteCard.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_navigation_home_to_calendarActivity)
        }
        return binding.root
    }
    /**
     * In this method we provide data to the donut graph that represents number of short and long trades
     * */
    private fun donut1(shorts: Int, longs: Int) {
        //The first pie chart and its labels
        val section1 = DonutSection(
            name = "Short_section",
            color = Color.parseColor("#FF0000"),
            amount = shorts.toFloat()
        )

        val section2 = DonutSection(
            name = "Long_section",
            color = Color.parseColor("#00BD00"),
            amount = longs.toFloat()
        )
        binding.donutView.cap = (longs+shorts).toFloat()
        binding.donutView.submitData(listOf(section1, section2))
        binding.DonutText.text = (longs+shorts).toString()
        binding.shortNumber.text = "Short positions: $shorts"
        binding.longNumber.text = "Long positions: $longs"
    }
    /**
     * In this method we provide data to the donut graph that represents number of win and lose trades
     * */
    private fun donut2(wins: Int, defeats: Int) {
        //The second pie chart and its labels
        val amount1  = if (wins + defeats != 0) ((wins * 100)/(wins + defeats)).toFloat() else 0f
        val section1 = DonutSection(
            name = "Win_section",
            color = Color.parseColor("#00BD00"),
            amount =  amount1
        )
        val amount2  = if (wins + defeats != 0) ((defeats * 100)/(wins + defeats)).toFloat() else 0f
        val section2 = DonutSection(
            name = "Defeat_section",
            color = Color.parseColor("#FF0000"),
            amount = amount2
        )
        binding.donutViewSecond.cap = 100F // 100% is all
        binding.donutViewSecond.submitData(listOf(section1, section2))
        binding.DonutTextRating.text = "$amount1%"
        binding.WinNumber.text = "Win positions: $wins"
        binding.WinNumber.setTextColor(Color.GREEN)
        binding.DefNumber.text = "Defeat positions: $defeats"
        binding.DefNumber.setTextColor(Color.RED)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}