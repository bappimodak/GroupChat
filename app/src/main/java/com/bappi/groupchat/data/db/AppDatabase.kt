package com.bappi.groupchat.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bappi.groupchat.data.dao.GroupDao
import com.bappi.groupchat.data.dao.MessageDao
import com.bappi.groupchat.data.dao.ParticipantsDao
import com.bappi.groupchat.data.dao.UserDao
import com.bappi.groupchat.data.entity.Groups
import com.bappi.groupchat.data.entity.Messages
import com.bappi.groupchat.data.entity.Participants
import com.bappi.groupchat.data.entity.User

@Database(entities = [User::class, Groups::class, Participants::class, Messages::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun groupDao(): GroupDao
    abstract fun messageDao(): MessageDao
    abstract fun participantsDao(): ParticipantsDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "group-chat-database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}