package com.example.data.login.locale.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.login.locale.entity.UserDb


@Dao
interface UserDao {
    @Query("SELECT * FROM user_table WHERE email == :email  and password = :password")
    fun getUser(email:String,password:String): UserDb?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(vararg user: UserDb)
    @Query("SELECT * FROM user_table")
    fun getAllUsers(): List<UserDb>
}