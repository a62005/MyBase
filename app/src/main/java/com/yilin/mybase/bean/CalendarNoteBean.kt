package com.yilin.mybase.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class CalendarNoteBean(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var title: String,
    var content: String,
    var dateTime: Long
)