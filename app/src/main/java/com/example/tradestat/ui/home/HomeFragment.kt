package com.example.tradestat.ui.home

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.futured.donut.DonutSection
import com.example.tradestat.databinding.FragmentHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
        CoroutineScope(Dispatchers.Main).launch {
            homeViewModel.updateShortsAndLongs()
            homeViewModel.updateWinsAndDefeats()
            shorts =  homeViewModel.shortNumber
            longs = homeViewModel.longNumber
            wins =  homeViewModel.winNumber
            defeats = homeViewModel.defeatNumber

            //The first pie chart and its labels
            var section1 = DonutSection(
                name = "Short_section",
                color = Color.parseColor("#DC143C"),
                amount = shorts.toFloat()
            )

            var section2 = DonutSection(
                name = "Long_section",
                color = Color.parseColor("#00FF00"),
                amount = longs.toFloat()
            )
            binding.donutView.cap = (longs+shorts).toFloat()
            binding.donutView.submitData(listOf(section1, section2))
            binding.DonutText.text = (longs+shorts).toString()
            binding.shortNumber.text = "Short positions: $shorts"
            binding.longNumber.text = "Long positions: $longs"

            //The second pie chart and its labels
            section1 = DonutSection(
                name = "Win_section",
                color = Color.parseColor("#00FF00"),
                amount = ((wins * 100)/(wins+defeats)).toFloat()
            )

            section2 = DonutSection(
                name = "Defeat_section",
                color = Color.parseColor("#DC143C"),
                amount = ((defeats * 100)/(wins+defeats)).toFloat()
            )
            binding.donutViewSecond.cap = 100F // 100% is all
            binding.donutViewSecond.submitData(listOf(section1, section2))
            binding.DonutTextRating.text = ((wins * 100)/(wins+defeats)).toString() + "%"
            binding.WinNumber.text = "Win positions: $wins"
            binding.WinNumber.setTextColor(Color.GREEN)
            binding.DefNumber.text = "Defeat positions: $defeats"
            binding.DefNumber.setTextColor(Color.RED)
        }
        val root: View = binding.root
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}