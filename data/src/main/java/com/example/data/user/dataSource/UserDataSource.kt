package com.example.data.user.dataSource

import com.example.data.entity.UserDb

interface UserDataSource{
    fun checkUser(email:String,pass:String):Boolean
    fun addUser(userDb: UserDb)
}