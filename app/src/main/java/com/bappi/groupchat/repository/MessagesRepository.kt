package com.bappi.groupchat.repository

import androidx.lifecycle.asLiveData
import com.bappi.groupchat.data.dao.MessageDao
import com.bappi.groupchat.data.entity.Messages

class MessagesRepository(private val messageDao: MessageDao) {
    fun getGroupMessages(userId: String) =
        messageDao.getGroupMessages(userId)

    fun insertGroupMessage(message: Messages) = messageDao.insertMessage(message)

}