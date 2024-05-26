package com.example.tradestat.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tradestat.data.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user_table WHERE email == :email  and password = :password")
    fun getUser(email:String,password:String): User
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(vararg user: User)
    @Query("SELECT * FROM user_table")
    fun getAllUsers(): List<User>
}