package com.bappi.groupchat.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bappi.groupchat.data.entity.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: User): Long

    @Query("SELECT * FROM user WHERE name=:userName")
    fun getUser(userName: String):  List<User>

    @Query("SELECT * FROM user")
    fun getAllUser():  List<User>
}