package com.bappi.groupchat.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @ColumnInfo(name = "name") val name: String?
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
    var isSelected: Boolean = false
}
