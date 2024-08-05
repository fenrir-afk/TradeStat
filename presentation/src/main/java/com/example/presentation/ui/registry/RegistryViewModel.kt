package com.example.presentation.ui.registry


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.user.usecase.RegistryUseCase
import com.example.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistryViewModel @Inject constructor(private val useCase: RegistryUseCase): ViewModel() {
    private var _userResultFow = MutableStateFlow<Boolean?>(null)
    val userResultFow = _userResultFow.asStateFlow()

    fun addUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            val result = useCase.execute(user)
            _userResultFow.emit(result)
        }
    }
    fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("^[a-zA-Z0-9.!#\$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*\$")
        return emailRegex.matches(email)
    }
}