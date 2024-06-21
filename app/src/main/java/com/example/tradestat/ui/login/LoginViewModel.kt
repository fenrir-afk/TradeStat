package com.example.tradestat.ui.login

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tradestat.repository.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(rep: BaseRepository):ViewModel() {
    private val repository: BaseRepository = rep
    var checkUserResult = MutableLiveData<Boolean>()
    fun checkUser(email:String,pass:String){
        viewModelScope.launch(Dispatchers.IO) {
            val user = repository.getUser(email,pass)
            checkUserResult.postValue(user == null)
        }
    }
}