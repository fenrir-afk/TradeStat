package com.example.tradestat.ui.login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tradestat.repository.BaseRepository
import com.example.tradestat.ui.results.ResultsViewModel

class LoginViewModelFactory(val application: Application, private val repository: BaseRepository):
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(application,repository) as T
    }
}