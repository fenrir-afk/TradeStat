package com.example.domain.login.usecase

import com.example.domain.login.LoginRepository

class LoginUseCase(private val loginRepository: LoginRepository) {
     fun execute(email:String,pass:String): Boolean{
        return loginRepository.login(email,pass)
    }
}