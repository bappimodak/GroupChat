package com.bappi.groupchat.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.bappi.groupchat.data.dao.ParticipantsDao
import com.bappi.groupchat.data.entity.Participants

class ParticipantsRepository(private val participantsDao: ParticipantsDao) {
    fun getGroups(userId: String) =
        participantsDao.getUsersGroups(userId)

    fun insertParticipants(participants: Participants) = participantsDao.insertParticipant(participants)
}