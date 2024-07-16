package com.example.tradestat.ui.registry

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tradestat.data.model.User
import com.example.tradestat.repository.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegistryViewModel(rep: BaseRepository): ViewModel() {
    private val repository: BaseRepository = rep
    var addUserResult = MutableLiveData<Boolean>()
    fun addUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            if(repository.getUser(user.email,user.pass) == null){
                repository.insertUser(user)
                addUserResult.postValue(true)
            }else{
                addUserResult.postValue(false)
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