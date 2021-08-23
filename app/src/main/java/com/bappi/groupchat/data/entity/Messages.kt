package com.bappi.groupchat.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
//    foreignKeys = [ForeignKey(
//        entity = Groups::class,
//        parentColumns = arrayOf("id"),
//        childColumns = arrayOf("groupId"),
//        onDelete = ForeignKey.CASCADE
//    )]
)
data class Messages(
    @ColumnInfo(name = "groupId") val groupId: String,
    @ColumnInfo(name = "userId") val userId: String,
    @ColumnInfo(name = "message") val message: String,
    @ColumnInfo(name = "likes") val likes: Int
){
    @PrimaryKey(autoGenerate = true) var uid: Int = 0
}
