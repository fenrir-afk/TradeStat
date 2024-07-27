package com.example.data.login.dataSource

import com.example.data.login.locale.dao.UserDao
import javax.inject.Inject

class LoginDataSourceImp @Inject constructor(private val userDao: UserDao):LoginDataSource {
    override fun checkUser(email:String,pass:String): Boolean {
        val newUser = userDao.getUser(email,pass)
        return newUser != null
    }
}