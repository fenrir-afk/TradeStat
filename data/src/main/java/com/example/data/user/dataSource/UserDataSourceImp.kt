package com.example.data.user.dataSource

import com.example.data.user.locale.dao.UserDao
import com.example.data.user.locale.entity.UserDb
import javax.inject.Inject

class UserDataSourceImp @Inject constructor(private val userDao: UserDao):UserDataSource {
    override fun checkUser(email:String,pass:String): Boolean {
        val newUser = userDao.getUser(email,pass)
        return newUser != null
    }
    override fun addUser(userDb: UserDb){
        userDao.insertUser(userDb)
    }
}