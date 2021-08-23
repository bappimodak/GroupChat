package com.bappi.groupchat.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.bappi.groupchat.data.dao.UserDao
import com.bappi.groupchat.data.entity.User

class UserRepository(private val userDao: UserDao) {
    fun insertUser(user: User) = userDao.insertUser(user)
    fun getUser(username: String) = userDao.getUser(username)
    fun getAllUser() = userDao.getAllUser()
}