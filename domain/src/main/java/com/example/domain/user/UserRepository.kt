package com.example.domain.user

import com.example.domain.user.entity.UserDm

interface UserRepository {
     fun checkUser(email:String,pass:String):Boolean
     fun addUser(user: UserDm)
}