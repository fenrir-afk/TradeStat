package com.example.domain.contracts


import com.example.domain.model.User

interface UserRepository {
     fun checkUser(email:String,pass:String):Boolean
     fun addUser(user: User)
}