package com.example.domain.user.usecase

import com.example.domain.contracts.UserRepository
import com.example.domain.model.User

class RegistryUseCase(private val userRepository: UserRepository) {
    fun execute(user: User): Boolean {
        val checkUser = userRepository.checkUser(user.email,user.pass)
        if (checkUser){
            return false
        }else{
            userRepository.addUser(user)
            return true
        }
    }
}