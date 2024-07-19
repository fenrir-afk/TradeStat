package com.example.tradestat.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tradestat.repository.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(rep: BaseRepository):ViewModel() {
    private val repository: BaseRepository = rep
    private var _checkUserResultFlow = MutableStateFlow<Boolean?>(null)
    val checkUserResultFlow = _checkUserResultFlow.asStateFlow()
    fun checkUser(email:String,pass:String){
        viewModelScope.launch(Dispatchers.IO) {
            val user = repository.getUser(email,pass)
            _checkUserResultFlow.emit(user == null)
        }
    }
}