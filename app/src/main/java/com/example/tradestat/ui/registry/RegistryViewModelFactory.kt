package com.example.tradestat.ui.registry

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tradestat.repository.BaseRepository
import com.example.tradestat.ui.login.LoginViewModel

class RegistryViewModelFactory(val application: Application, private val repository: BaseRepository):
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegistryViewModel(application,repository) as T
    }
}