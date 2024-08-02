package com.example.domain.user.usecase

import com.example.domain.contracts.UserRepository

class LoginUseCase(private val userRepository: UserRepository) {
     fun execute(email:String,pass:String): Boolean{
        return userRepository.checkUser(email,pass)
    }
}