package com.example.data.login.dataSource
import com.example.data.login.locale.entity.UserDb

interface LoginDataSource{
    fun checkUser(email:String,pass:String):Boolean
}