package com.example.tradestat.ui.news
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
        if (counter == 0){
            newsViewModel.updateQuotes()
        }
        counter++
        newsViewModel.direction.observe(viewLifecycleOwner,Observer{
            var a = newsViewModel.quote
            var bool = it
        })
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}