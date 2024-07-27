package com.example.data.mapper

import com.example.data.user.locale.entity.UserDb
import com.example.domain.user.entity.UserDm

fun UserDm.toDbData() = UserDb(
    id = id,
    login = login,
    email = email,
    pass = pass
)
