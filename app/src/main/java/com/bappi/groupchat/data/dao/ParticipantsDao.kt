package com.bappi.groupchat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bappi.groupchat.data.entity.Participants
import kotlinx.coroutines.flow.Flow

@Dao
interface ParticipantsDao {
    @Insert
    fun insertParticipant(participants: Participants)

    @Query("SELECT * FROM participants WHERE userId=:userId")
    fun getUsersGroups(userId: String): List<Participants>
}