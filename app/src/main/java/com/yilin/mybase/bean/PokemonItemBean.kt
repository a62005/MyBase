package com.yilin.mybase.bean

data class PokemonItemBean(
    val id: String,
    val name: String,
    val type: String,
    val imageUrl: String,
    val attack: Int,
    val defense: Int,
    val speed: Int,
    var isFavorite: Boolean
)
