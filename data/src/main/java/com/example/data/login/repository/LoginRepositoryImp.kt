package com.example.data.login.repository

import com.example.data.login.dataSource.LoginDataSource
import com.example.data.mapper.toDbData
import com.example.domain.login.LoginRepository
import com.example.domain.login.entity.User
import javax.inject.Inject

class LoginRepositoryImp @Inject constructor(private val loginDataSource: LoginDataSource):LoginRepository {
    override fun login(email:String,pass:String): Boolean {
        return loginDataSource.checkUser(email,pass)
    }

}