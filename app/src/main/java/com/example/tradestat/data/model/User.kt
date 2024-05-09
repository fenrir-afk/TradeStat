package com.example.tradestat.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * This model represent average user of the app
 * @param id the id of the user
 * @param login is the users name
 * @param email is the email address of the user
 * @param pass is the users password
* */
@Entity(tableName = "user_tabel")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "login") val login: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "password") val pass: String,
)