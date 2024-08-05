package com.example.presentation.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.user.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase):ViewModel() {
    private var _checkUserResultFlow = MutableStateFlow<Boolean?>(null)
    val checkUserResultFlow = _checkUserResultFlow.asStateFlow()
    fun checkUser(email:String,pass:String){
        viewModelScope.launch(Dispatchers.IO){
            val result = loginUseCase.execute(email,pass)
            if (result){
                _checkUserResultFlow.emit(true)
            }else{
                _checkUserResultFlow.emit(false)
            }
        }
    }
}