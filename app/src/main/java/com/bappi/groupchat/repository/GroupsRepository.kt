package com.bappi.groupchat.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.bappi.groupchat.data.dao.GroupDao
import com.bappi.groupchat.data.entity.Groups

class GroupsRepository(private val groupDao: GroupDao) {
     fun insertGroup(group: Groups) = groupDao.insertGroup(group)
     fun getGroup(groupName: String) = groupDao.getGroup(groupName)
}