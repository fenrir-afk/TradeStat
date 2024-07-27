package com.example.data.user.repository

import com.example.data.mapper.toDbData
import com.example.data.user.dataSource.UserDataSource
import com.example.domain.user.UserRepository
import com.example.domain.user.entity.UserDm
import javax.inject.Inject

class UserRepositoryImp @Inject constructor(private val userDataSource: UserDataSource):UserRepository {
    override fun checkUser(email: String, pass: String): Boolean {
        return userDataSource.checkUser(email,pass)
    }

    override fun addUser(user: UserDm) {
        userDataSource.addUser(user.toDbData())
    }

}