package com.example.tradestat.ui.trade

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tradestat.repository.BaseRepository

class TradeViewModelFactory(val application: Application,private val repository: BaseRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TradeViewModel(application,repository) as T
    }
}