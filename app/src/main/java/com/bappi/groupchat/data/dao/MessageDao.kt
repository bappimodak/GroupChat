package com.bappi.groupchat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bappi.groupchat.data.entity.Messages
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Insert
    fun insertMessage(messages: Messages)

    @Query("SELECT * FROM messages WHERE groupId = :groupId")
    fun getGroupMessages(groupId: String): Flow<List<Messages>>
}