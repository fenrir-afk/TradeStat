package com.example.tradestat.ui.news
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tradestat.R
import com.example.tradestat.data.database.TradeDatabase
import com.example.tradestat.databinding.FragmentNewsBinding
import com.example.tradestat.repository.TradesRepository

class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    var counter = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)

        val repository = TradesRepository(TradeDatabase.getDatabase(requireContext()))
        val viewModelProviderFactory = NewsViewModelFactory(repository)
        val newsViewModel = ViewModelProvider(this, viewModelProviderFactory)[NewsViewModel::class.java]
        newsViewModel.stockMarketValues.observe(viewLifecycleOwner,Observer{
            updateStockMarketQuotes(it)
        })
        newsViewModel.quotesLiveData.observe(viewLifecycleOwner,Observer{
            updateQuotes(it)
        })
        return binding.root
    }
    private fun updateStockMarketQuotes(mutableMap: MutableMap<String, String>) {
       if (mutableMap.isNotEmpty()){
           binding.firstMoexTitle.text = "MOEX"
           binding.secondMoexTitle.text = "SBER"
           binding.thirdMoexTitle.text = "GAZP"
           binding.firstMoexValue.text = mutableMap["MOEX"]
           binding.secondMoexValue.text = mutableMap["SBER"]
           binding.thirdMoexValue.text = mutableMap["GAZP"]
       }
    }
    private fun updateQuotes(triples: MutableList<Triple<String, Boolean, String>>) {
        if (triples.size > 0){
           binding.quote1.text = "${triples[0].third} ${triples[0].first}"
           binding.quote2.text = "${triples[1].third} ${triples[1].first}"
        }else{
           return
        }
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}