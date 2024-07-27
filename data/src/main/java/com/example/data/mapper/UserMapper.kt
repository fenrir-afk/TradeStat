package com.example.data.mapper

import com.example.data.login.locale.entity.UserDb
import com.example.domain.login.entity.User

fun User.toDbData() = UserDb(
    id = id,
    login = login,
    email = email,
    pass = pass
)
