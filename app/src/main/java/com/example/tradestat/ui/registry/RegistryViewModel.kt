package com.example.tradestat.ui.registry


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tradestat.data.model.User
import com.example.tradestat.repository.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegistryViewModel(rep: BaseRepository): ViewModel() {
    private val repository: BaseRepository = rep
    private var _userResultFow = MutableStateFlow<Boolean?>(null)
    val userResultFow = _userResultFow.asStateFlow()

    fun addUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            if(repository.getUser(user.email,user.pass) == null){
                repository.insertUser(user)
                _userResultFow.emit(true)
            }else{
                _userResultFow.emit(false)
            }
        }
    }
    fun getUsers(){
        viewModelScope.launch(Dispatchers.IO) {
            val list = repository.getAllUsers()
            println(list)
        }
    }
}