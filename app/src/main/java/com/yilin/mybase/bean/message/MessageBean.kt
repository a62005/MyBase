package com.yilin.mybase.bean.message

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yilin.mybase.utils.DateUtil

@Entity(tableName = "message_table")
open class MessageBean(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val date: String = DateUtil.getCurrentTime(),
    var isRead: Boolean = false
)