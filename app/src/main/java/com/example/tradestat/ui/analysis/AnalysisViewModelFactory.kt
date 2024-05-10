package com.example.tradestat.ui.analysis

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tradestat.repository.BaseRepository

class AnalysisViewModelFactory(val application: Application, private val repository: BaseRepository):
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AnalysisViewModel(application,repository) as T
    }
}