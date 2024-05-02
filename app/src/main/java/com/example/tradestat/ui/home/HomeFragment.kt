package com.example.tradestat.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import app.futured.donut.DonutSection
import com.example.tradestat.R
import com.example.tradestat.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!
    var shorts:Int = 0
    var longs:Int = 0
    var wins:Int = 0
    var defeats:Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        homeViewModel.updateNumbers()
        homeViewModel.getNumberList.observe(viewLifecycleOwner) {
            shorts = it[0]
            longs = it[1]
            wins = it[2]
            defeats = it[3]
            donut1()
            donut2()
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
        return binding.root
    }
    /**
     * In this method we provide data to the donut graph that represents number of short and long trades
     * */
    private fun donut1(){
        //The first pie chart and its labels
        val section1 = DonutSection(
            name = "Short_section",
            color = Color.parseColor("#DC143C"),
            amount = shorts.toFloat()
        )

        val section2 = DonutSection(
            name = "Long_section",
            color = Color.parseColor("#00FF00"),
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
    private fun donut2() {
        //The second pie chart and its labels
        val amount1  = if (this.wins + this.defeats != 0) ((this.wins * 100)/(this.wins + this.defeats)).toFloat() else 0f
        val section1 = DonutSection(
            name = "Win_section",
            color = Color.parseColor("#00FF00"),
            amount =  amount1
        )
        val amount2  = if (this.wins + this.defeats != 0) ((this.defeats * 100)/(this.wins + this.defeats)).toFloat() else 0f
        val section2 = DonutSection(
            name = "Defeat_section",
            color = Color.parseColor("#DC143C"),
            amount = amount2
        )
        binding.donutViewSecond.cap = 100F // 100% is all
        binding.donutViewSecond.submitData(listOf(section1, section2))
        binding.DonutTextRating.text = "$amount1%"
        binding.WinNumber.text = "Win positions: ${this.wins}"
        binding.WinNumber.setTextColor(Color.GREEN)
        binding.DefNumber.text = "Defeat positions: ${this.defeats}"
        binding.DefNumber.setTextColor(Color.RED)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}