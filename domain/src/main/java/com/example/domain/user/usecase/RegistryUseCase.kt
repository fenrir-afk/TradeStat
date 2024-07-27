package com.example.domain.user.usecase

import com.example.domain.user.UserRepository
import com.example.domain.user.entity.UserDm

class RegistryUseCase(private val userRepository: UserRepository) {
    fun execute(user: UserDm): Boolean {
        val checkUser = userRepository.checkUser(user.email,user.pass)
        if (checkUser){
            return false
        }else{
            userRepository.addUser(user)
            return true
        }
    }
}