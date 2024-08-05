package com.example.data.mapper

import com.example.data.entity.UserDb
import com.example.domain.model.User

fun User.toDbData() = UserDb(
    id = id,
    login = login,
    email = email,
    pass = pass
)
