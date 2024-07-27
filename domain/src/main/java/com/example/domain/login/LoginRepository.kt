package com.example.domain.login

import com.example.domain.login.entity.User

interface LoginRepository {
     fun login(email:String,pass:String):Boolean
}