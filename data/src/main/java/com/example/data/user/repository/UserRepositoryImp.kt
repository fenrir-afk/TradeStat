package com.example.data.user.repository

import com.example.data.mapper.toDbData
import com.example.data.user.dataSource.UserDataSource
import com.example.domain.contracts.UserRepository
import com.example.domain.model.User
import javax.inject.Inject

class UserRepositoryImp @Inject constructor(private val userDataSource: UserDataSource):
    UserRepository {
    override fun checkUser(email: String, pass: String): Boolean {
        return userDataSource.checkUser(email,pass)
    }

    override fun addUser(user: User) {
        userDataSource.addUser(user.toDbData())
    }

}