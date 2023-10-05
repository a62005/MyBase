package com.yilin.mybase.bean

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "pokemon")
data class PokemonBean(
    @PrimaryKey
    val id: String,
    val name: String,
    @SerializedName("typeofpokemon")
    val types: List<String>,
    var type: String = "",
    var isFavorite: Boolean = false
)
