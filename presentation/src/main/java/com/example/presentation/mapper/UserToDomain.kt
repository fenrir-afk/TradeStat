package com.example.presentation.mapper

import com.example.domain.user.entity.UserDm
import com.example.presentation.data.model.User


fun User.toDomain() = UserDm(
    id = id,
    login = login,
    email = email,
    pass = pass
)
