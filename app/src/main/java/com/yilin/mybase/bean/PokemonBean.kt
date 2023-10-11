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
    @SerializedName("imageurl")
    val imageUrl: String,
    val attack: Int,
    val defense: Int,
    val speed: Int,
    val total: Int,
    val hp: Int,
    @SerializedName("xdescription")
    val description: String,
    val height: String,
    val weight: String,
    var isFavorite: Boolean = false
)
