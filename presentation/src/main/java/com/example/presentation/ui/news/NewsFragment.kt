package com.example.presentation.ui.news
import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.presentation.R
import com.example.presentation.databinding.FragmentNewsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFragment : Fragment() {
    companion object{
        const val BKS_NEWS_URL = "https://bcs-express.ru/ozhidaemye-sobytiya"
        const val FOREX_NEWS = "https://ru.investing.com/news/forex-news"
        const val CRYPTO_NEWS = "https://ru.investing.com/news/cryptocurrency-news"
    }
    enum class Stocks{
        MOEX,
        SBER,
        GAZP
    }

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private val newsViewModel:NewsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        checkTheme()
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
        clickListenersFun()
        return binding.root
    }
    private fun clickListenersFun(){
        binding.bksNews.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(BKS_NEWS_URL))
            startActivity(intent)
        }
        binding.forex.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(FOREX_NEWS))
            startActivity(intent)
        }
        binding.crypta.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(CRYPTO_NEWS))
            startActivity(intent)
        }
    }
    private fun checkTheme() {
        val nightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if (nightMode == Configuration.UI_MODE_NIGHT_YES) {
            binding.firstMoexValue.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
            binding.secondMoexValue.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
            binding.thirdMoexValue.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
            binding.quote1.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
            binding.quote2.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
        } else {
            binding.firstMoexValue.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
            binding.secondMoexValue.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
            binding.thirdMoexValue.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
            binding.quote1.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
            binding.quote2.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
        }
    }

    private fun updateStockMarketQuotes(mutableMap: MutableMap<String, String>) {
       if (mutableMap.isNotEmpty()){
           Stocks.entries.forEachIndexed{ index, stock ->
               when(index){
                   0 -> {
                       binding.firstMoexTitle.text = stock.toString()
                       binding.firstMoexValue.text = mutableMap[stock.toString()]
                   }
                   1 -> {
                       binding.secondMoexTitle.text = stock.toString()
                       binding.secondMoexValue.text = mutableMap[stock.toString()]
                   }
                   2 -> {
                       binding.thirdMoexTitle.text = stock.toString()
                       binding.thirdMoexValue.text = mutableMap[stock.toString()]
                   }
                   else ->{
                       println("Error: there are only 3 views for stocks")
                   }
               }
           }
       }
    }
    @SuppressLint("SetTextI18n")
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