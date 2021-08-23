package com.bappi.groupchat.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
//    foreignKeys = [ForeignKey(
//        entity = User::class,
//        parentColumns = arrayOf("uid"),
//        childColumns = arrayOf("userId"),
//        onDelete = CASCADE
//    )]
)
data class Participants(
    @ColumnInfo(name = "userId") val userId: Int,
    @ColumnInfo(name = "groupId") val groupId: Int,
    @ColumnInfo(name = "groupName") val groupName: String,
) {
    @PrimaryKey(autoGenerate = true) var Uid: Int = 0
}
