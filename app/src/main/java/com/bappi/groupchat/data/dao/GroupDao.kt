package com.bappi.groupchat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bappi.groupchat.data.entity.Groups
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupDao {
    @Insert
    fun insertGroup(groups: Groups): Long

    @Query("SELECT * FROM GROUPS WHERE name = :groupName")
    fun getGroup(groupName: String): List<Groups>

    @Query("SELECT * FROM Groups")
    fun getAllGroups(): List<Groups>

    @Query("SELECT * FROM Groups WHERE uid = :groupId")
    fun getUserGroups(groupId: String): Flow<List<Groups>>
}