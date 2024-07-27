package com.example.data.user.dataSource

import com.example.data.user.locale.entity.UserDb

interface UserDataSource{
    fun checkUser(email:String,pass:String):Boolean
    fun addUser(userDb: UserDb)
}