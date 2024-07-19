package com.example.tradestat.ui.news
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.tradestat.R
import com.example.tradestat.data.database.TradeDatabase
import com.example.tradestat.databinding.FragmentNewsBinding
import com.example.tradestat.repository.TradesRepository
import com.example.tradestat.utils.BaseViewModelFactory
import kotlinx.coroutines.launch

class NewsFragment : Fragment() {
    enum class Stocks{
        MOEX,
        SBER,
        GAZP
    }

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private val newsViewModel:NewsViewModel by viewModels {
        val repository = TradesRepository(TradeDatabase.getDatabase(requireContext()))
        BaseViewModelFactory(repository)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                newsViewModel.stockMarketValuesFlow.collect{
                    updateStockMarketQuotes(it)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                newsViewModel.quitesFlow.collect{
                    updateQuotes(it)
                }
            }
        }
        return binding.root
    }
    private fun updateStockMarketQuotes(mutableMap: MutableMap<String, String>) {
       if (mutableMap.isNotEmpty()){
           Stocks.entries.forEachIndexed{ index, stocks ->
               when(index){
                   0 -> {
                       binding.firstMoexTitle.text = stocks.toString()
                       binding.firstMoexValue.text = mutableMap[stocks.toString()]
                   }
                   1 -> {
                       binding.secondMoexTitle.text = stocks.toString()
                       binding.secondMoexValue.text = mutableMap[stocks.toString()]
                   }
                   2 -> {
                       binding.thirdMoexTitle.text = stocks.toString()
                       binding.thirdMoexValue.text = mutableMap[stocks.toString()]
                   }
                   else ->{
                       println("Error: there are only 3 views for stocks")
                   }
               }
           }
       }
    }
    private fun updateQuotes(triples: MutableList<Triple<String, Boolean, String>>) {
        if (triples.size == 2){
           binding.quote1.text = "${triples[0].third} ${triples[0].first}"
           binding.quote2.text = "${triples[1].third} ${triples[1].first}"
            if (triples[0].second){
                binding.quoteIm1.setImageResource(R.drawable.greenarrow)
            }else{
                binding.quoteIm1.setImageResource(R.drawable.redarrow)
            }
            if (triples[1].second){
                binding.quoteIm2.setImageResource(R.drawable.greenarrow)
            }else{
                binding.quoteIm2.setImageResource(R.drawable.redarrow)
            }
        }else{
           return
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}