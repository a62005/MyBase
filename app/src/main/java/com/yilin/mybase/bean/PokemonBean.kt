package com.yilin.mybase.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
data class PokemonBean(
    @PrimaryKey
    val id: String,
    val name: String,
    var isFavorite: Boolean = false
)
